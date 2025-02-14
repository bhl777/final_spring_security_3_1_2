// Функция выполнится автоматически
(function() {

    // Функция выполнится после полной загрузки ресурсов
    document.addEventListener("DOMContentLoaded", function(event){

// Модальное окно редактора открывается
$('#editModalUser').on('show.bs.modal', function (event) {

    // Очистка поля модального окна
    $('.modal-body', event.target).html('<p class="text-center text-muted m-5">Загрузка ...</p>');

    // ID пользователя в аттрибуте HTML-элемента кнопки вызывающей модальное окно
    let uid = $(event.relatedTarget).data('userid');

    // Дополнение адреса формы
    //$('.modal-content form:eq(0)', event.target).prop('action', '/admin/update?id='+uid);

    // Загрузка странички редактирования с нужными параметрами
    $.ajax({url: '/admin/update', data: {'id':uid}, type: 'GET', cache: false, dataType: 'html',
        // Успешно
        success: function(result){
            $('.modal-body', event.target).html(result);
        },
        // Ошибка
        error: function(xhr){
            $('.modal-body', event.target).html('<p class="text-center text-muted m-5">Ошибка загрузки формы редактирования пользователя</p>');
        }
    });

});

// Модальное окно удаления открывается
$('#delModalUser').on('show.bs.modal', function (event) {

    // Очистка поля модального окна
    $('.modal-body', event.target).html('<p class="text-center text-muted m-5">Загрузка ...</p>');

    // ID пользователя в аттрибуте кнопки вызывающей модальное окно
    let uid = $(event.relatedTarget).data('userid');

    // Дополнение адреса формы
    //$('.modal-content form:eq(0)', event.target).prop('action', '/admin/delete?id='+uid);

    // Загрузка странички удаления с нужными параметрами
    $.ajax({url: '/admin/delete', data: {'id':uid}, type: 'GET', cache: false, dataType: 'html',
        // Успешно
        success: function(result){
            $('.modal-body', event.target).html(result);
        },
        // Ошибка
        error: function(xhr){
            $('.modal-body', event.target).html('<p class="text-center text-muted m-5">Ошибка загрузки формы удаления пользователя</p>');
        }
    });

});

// Модальное окно закрылось
$('.editModal-DIV, .delModal-DIV').on('hidden.bs.modal', function (event) {
    $('.modal-body', event.target).html('');
});

    });

})();