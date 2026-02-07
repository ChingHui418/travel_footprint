let apiUrl = '/footprints'; 

document.addEventListener('DOMContentLoaded', loadFootprints);

function loadFootprints() {
    fetch(apiUrl)
        .then(response => response.json())
        .then(data => {
            let container = document.getElementById('app');
            container.innerHTML = ''; 

            if (data.length === 0) {
                container.innerHTML = '<p style="text-align:center; width:100%; color:#888">目前還沒有足跡，快按下新增按鈕吧！</p>';
                return;
            }

            data.sort((a, b) => new Date(b.travelDate) - new Date(a.travelDate));

            data.forEach(fp => {
                let card = document.createElement('div');
                card.className = 'card';
                
                let imageSrc = fp.photoUrl ? fp.photoUrl : 'https://via.placeholder.com/300x200?text=No+Image';

                // 計算天數
                let dateDisplay = fp.travelDate;

                if (fp.endDate) {
                    let start = new Date(fp.travelDate);
                    let end = new Date(fp.endDate);
                    let diffTime = end - start;

                    if (diffTime >= 0) {
                        let diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24)) + 1; 
                        dateDisplay = `${fp.travelDate} ~ ${fp.endDate} (共 ${diffDays} 天)`;
                    }
                }

                card.innerHTML = `
                    <button class="btn-edit" onclick="editFootprint(${fp.id})">✏️</button>
                    <button class="btn-delete" onclick="deleteFootprint(${fp.id})">🗑️</button>
                    <img src="${imageSrc}" alt="${fp.title}">
                    <div class="card-content">
                        <span class="location-tag">📍 ${fp.location}</span>
                        <div class="card-title">${fp.title}</div>
                        <span class="card-date">📅 ${dateDisplay}</span>
                        <p class="card-text">${fp.description}</p>
                    </div>
                `;
                container.appendChild(card);
            });
        })
        .catch(err => console.error('Error:', err));
}
// 編輯
function editFootprint(id) {
    fetch(`${apiUrl}/${id}`)
    .then(response => response.json())
        .then(data => {
            document.getElementById('inputTitle').value = data.title;
            document.getElementById('inputLocation').value = data.location;
            document.getElementById('inputDate').value = data.travelDate;
            document.getElementById('inputEndDate').value = data.endDate;
            document.getElementById('inputDesc').value = data.description;
            document.getElementById('inputPhoto').value = data.photoUrl;

            document.getElementById('editId').value = data.id;
            document.getElementById('modalTitle').innerText = "✏️ 編輯足跡";

            document.getElementById('modalOverlay').style.display = 'flex';
        })
        .catch(err => Swal.fire('錯誤', '讀取資料失敗', 'error'));
}
// 刪除
function deleteFootprint(id) {
    Swal.fire({
        title: '確定要刪除嗎？',
        text: "刪掉就救不回來囉！(除非你會寫 SQL 救回來 😁)",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#d33',
        cancelButtonColor: '#3085d6',
        confirmButtonText: '狠心刪除',
        cancelButtonText: '再想想'
    }).then((result) => {
        if (result.isConfirmed) {
            fetch(`${apiUrl}/${id}`, {
                method: 'DELETE'
            })
            .then(response => {
                if (response.ok) {
                    Swal.fire(
                        '刪除成功！',
                        '那段回憶已經隨風而去了～',
                        'success'
                    );
                    loadFootprints();
                } else {
                    Swal.fire('失敗', '刪除失敗，請檢查後端', 'error');
                }
            });
        }
    })
}
// 新增
function openModal() {
    document.getElementById('inputTitle').value = '';
    document.getElementById('inputLocation').value = '';
    document.getElementById('inputDesc').value = '';
    document.getElementById('inputPhoto').value = '';
    document.getElementById('inputDate').valueAsDate = new Date();
    document.getElementById('inputEndDate').value = '';

    document.getElementById('editId').value = '';
    document.getElementById('modalTitle').innerText = "✨ 新增足跡";
    document.getElementById('modalOverlay').style.display = 'flex';
}
// 關閉
function closeModal() {
    document.getElementById('modalOverlay').style.display = 'none';
}
// 儲存（新增或編輯）
function saveFootprint() {
    let id = document.getElementById('editId').value;
    let title = document.getElementById('inputTitle').value;
    let location = document.getElementById('inputLocation').value;
    let date = document.getElementById('inputDate').value;
    let endDate = document.getElementById('inputEndDate').value;
    let desc = document.getElementById('inputDesc').value;
    let photo = document.getElementById('inputPhoto').value;

    if(!title || !date) {
        Swal.fire('標題和日期是必填的哦！', 'warning');
        return;
    }

    let footprintData = {
        title: title,
        location: location,
        travelDate: date,
        endDate: endDate,
        description: desc,
        photoUrl: photo
    };

    let requestMethod = id ? 'PUT' : 'POST';
    let requestUrl = id ? `${apiUrl}/${id}` : apiUrl;

    fetch(requestUrl, {
        method: requestMethod,
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(footprintData)
    })
    .then(response => {
        if(response.ok) {
            closeModal();
            loadFootprints();

            Swal.fire({
                position: 'top-end',
                icon: 'success',
                title: id ? '修改成功！' : '新增成功！',
                showConfirmButton: false,
                timer: 1500
            });
        } else {
            Swal.fire('失敗', '儲存失敗', 'error');
        }
    })
    .catch(err => console.error('Error:', err));
}