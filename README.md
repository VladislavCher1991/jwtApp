# jwtApp
Spring based Rest application with JWT security

В выполнении тестового задания я немного отошел от критериев – неправильно понял требования задачи. Было реализовано приложение с авторизацией и аутентификацией 
по JWT на основе spring security. Поэтому пришлось изменить названия некоторых полей, но функционал остался в соответствии с заданием.
Работа с базой данных реализована c помощью Spring JPA. База данных при каждом запуске приложения обновляется в соответствии с захардкоденными данными 
в классе JwtAppApplication. Создаются три таблицы: таблица пользователей и две связанные с ней - роли и сообщения. 

Токен генерируется в пакете filters в классе CustomAuthenticationFilter. Чтобы получить токен отправляем POST запрос на localhost:8080/login 
В теле запроса ключ, соответствующий имени пользователя, называем username:
{
    "username": "admin",
    "password": "1111"
}
или используем стандартную логин страницу спринга (localhost:8080/login). 
Имена пользователей: admin и user пароль в обоих случаях 1111, роли соответствуют именам.

Проверка входящих запросов на валидность токена осуществляется в пакете filters в классе CustomAuthorizationFilter.

Работа с входящими сообщениями реализована в пакете controllers в классе AppUserController и в пакете  service в классе UserService в методе proceedIncomingMessage 
(метод называется одинаково в обоих классах). Поле message было изменено на text, чтобы избежать «некрасивых» конструкций в коде (к примеру message.getMessage() и тп.). 
Пример тела запроса:
{
    "text": "history some text",
    "name": "admin"
}
Для авторизации в заголовке запроса добавляем ключ “Authorization” со значением «Bearer ‘Токен’». Пример заголовка запроса:

KEY: Authorization

VALUE: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjpbIlJPTEVfQURNSU4iLCJST0xFX1VTRVIiXSwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL2xvZ2luIiwiZXhwIjoxNjM5MTMxMzQzfQ.oikczfTpMvR0Ifht0ufBmYIaZE9WF6rnavUSO_2nsJw

Запросы на сохранения сообщений в бд отправлять на http://localhost:8080/messages/save POST методом. Для доступа к этому методу авторизация не обязательна. 
Для проверки работы авторизации можно отправить GET запрос на http://localhost:8080/users или http://localhost:8080/admin (к последнему имеют доступ только админы,
первую откроет и юзер и админ). 

Остальные классы и интерфейсы, реализованные в приложении необходимы для корректной работы спринга.

Метод main находится в классе JwtAppApplication. Перед запуском необходимо обновить данные о БД в пропертях проекта.
