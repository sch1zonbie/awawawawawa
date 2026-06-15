const API = '/api/tasks';
let editingId = null;
let creatingStatus = null;
let currentWorkerId = null;

async function loadTasks() {
    const res = await fetch(API);
    const tasks = await res.json();
    ['NEW', 'IN_PROGRESS', 'DONE'].forEach(s => {
        document.getElementById('col-' + s).innerHTML = '';
    });
    tasks.forEach(renderCard);
    updateCounts();
}

async function loadWorkers() {
    const res = await fetch('/api/workers');
    const workers = await res.json();

    const dropdown = document.getElementById('WorkerDropdown');
    const assigneeSelect = document.getElementById('create-assignee');

    dropdown.innerHTML = workers
        .map(w => `<a href="#" onclick="loadBoard(${w.id})">${escHtml(w.name)}</a>`)
        .join('');

    assigneeSelect.innerHTML =
        `<option value="">Исполнитель</option>` +
        workers.map(w =>
            `<option value="${w.id}">${escHtml(w.name)}</option>`
        ).join('');
}

function renderCard(task) {
    const card = document.createElement('div');
    card.className = 'task-card';
    card.dataset.id = task.id;
    card.draggable = true;

    card.innerHTML = `
    <div class="task-title">${escHtml(task.title)}</div>
`;

    card.addEventListener('dragstart', onDragStart);
    card.addEventListener('dragend', onDragEnd);
    card.querySelector('.task-title').addEventListener('click', () => openCreateModal(task.status, task));

    const col = document.getElementById('col-' + task.status);
    col.appendChild(card);
}

function updateCounts() {
    ['NEW', 'IN_PROGRESS', 'DONE'].forEach(s => {
        const count = document.getElementById('col-' + s).children.length;
        const col = document.querySelector(`.kanban-col[data-status="${s}"]`);
        col.querySelector('.col-count').textContent = count;
    });
}

function openCreateModal(status, task = null) {
    creatingStatus = status;
    editingId = task ? task.id : null;

    document.querySelector('#create-modal .modal-header span').textContent =
        task ? 'Отредактируйте задачу' : 'Новая задача';
    document.getElementById('create-modal-save').textContent =
        task ? 'Сохранить' : 'Создать';
    document.getElementById('create-modal-delete').style.display =
        task ? 'inline-block' : 'none';

    document.getElementById('create-title').value    = task?.title       ?? '';
    document.getElementById('create-desc').value     = task?.description ?? '';
    document.getElementById('create-deadline').value = task?.deadline    ?? '';
    document.getElementById('create-assignee').value = task?.assignee    ?? '';
    document.getElementById('create-priority').value = task?.priority    ?? 'MEDIUM';

    document.getElementById('create-modal').style.display = 'flex';
    document.getElementById('create-title').focus();
}

function closeCreateModal() {
    document.getElementById('create-modal').style.display = 'none';
    creatingStatus = null;
    editingId = null;
}

document.querySelectorAll('.col-add-btn').forEach(btn => {
    btn.addEventListener('click', () => openCreateModal(btn.dataset.status));
});

document.getElementById('create-modal-close').addEventListener('click', closeCreateModal);
document.getElementById('create-modal-cancel').addEventListener('click', closeCreateModal);

document.getElementById('create-modal').addEventListener('click', e => {
    if (e.target === document.getElementById('create-modal')) closeCreateModal();
});

document.getElementById('create-modal-save').addEventListener('click', async () => {
    const title = document.getElementById('create-title').value.trim();
    const deadline = document.getElementById('create-deadline').value;
    const assignee = document.getElementById('create-assignee').value;

    if (!title) {
        document.getElementById('create-title').focus();
        return;
    }

    if (!assignee) {
        document.getElementById('create-assignee').focus();
        return;
    }

    if (!deadline) {
        document.getElementById('create-deadline').focus();
        return;
    }

    const body = {
        title,
        status: creatingStatus,
        description: document.getElementById('create-desc').value.trim(),
        deadline,
        assignee: { id: assignee },   // ← было просто assignee
        priority: document.getElementById('create-priority').value,
    };

    if (editingId) {
        await fetch(`${API}/${editingId}`, {
            method: 'PATCH',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(body)
        });
    } else {
        const res = await fetch(API, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(body)
        });
        const task = await res.json();
        renderCard(task);
        updateCounts();
    }

    closeCreateModal();
    // loadTasks();
});

document.getElementById('create-modal-delete').addEventListener('click', async () => {
    await fetch(`${API}/${editingId}`, { method: 'DELETE' });
    closeCreateModal();
    loadTasks();
});

let draggedId = null;

function onDragStart(e) {
    draggedId = e.currentTarget.dataset.id;
    e.currentTarget.classList.add('dragging');
}

function onDragEnd(e) {
    e.currentTarget.classList.remove('dragging');
}

document.querySelectorAll('.kanban-col').forEach(col => {
    col.addEventListener('dragover', e => {
        e.preventDefault();
        col.classList.add('drag-over');
    });
    col.addEventListener('dragleave', () => {
        col.classList.remove('drag-over');
    });
    col.addEventListener('drop', async e => {
        e.preventDefault();
        col.classList.remove('drag-over');
        const newStatus = col.dataset.status;
        await fetch(`${API}/${draggedId}/status`, {
            method: 'PATCH',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ status: newStatus })
        });
        if (currentWorkerId) {
            loadBoard(currentWorkerId);
        } else {
            loadTasks();
        }
        draggedId = null;
    });
});

function escHtml(str) {
    return str
        .replace(/&/g, '&amp;')
        .replace(/</g, '&lt;')
        .replace(/>/g, '&gt;');
}

function updateClock() {
    const now = new Date();
    const h = String(now.getHours()).padStart(2, '0');
    const m = String(now.getMinutes()).padStart(2, '0');
    document.getElementById('clock-time').textContent = h + ':' + m;
}
updateClock();
setInterval(updateClock, 1000);

function WorkerMenu() {
    const dropdown = document.getElementById("WorkerDropdown");
    const sidebar  = document.querySelector(".sidebar");
    const isOpen   = dropdown.classList.toggle("show");

    sidebar.style.marginTop = isOpen
        ? dropdown.scrollHeight + 'px'
        : '0';
}

loadWorkers();
loadBoard(1);

async function loadBoard(workerId) {
    currentWorkerId = workerId;
    const res = await fetch(`/api/boards/worker/${workerId}`);
    const board = await res.json();  // ← эта строка потерялась

    ['NEW', 'IN_PROGRESS', 'DONE'].forEach(s => {
        document.getElementById('col-' + s).innerHTML = '';
    });

    board.tasks.forEach(renderCard);
    updateCounts();
}
