INSERT INTO animal (id, name, age, breed, healthy, animal_type, shelter_id)
VALUES (1, 'Пушок', 3, 'Сфинкс', true, 'CAT', 1),
       (2, 'Снежок', 11, 'Беспородная', false, 'CAT', 1),
       (3, 'Барсик', 5, 'Сибирская', true, 'CAT', 1),
       (4, 'Бонифаций', 9, 'Шотландская-веслоухая', true, 'CAT', 1),
       (5, 'Марсель', 4, 'Корниш-рекс', true, 'CAT', 1),
       (6, 'Герда', 4, 'Доберман', true, 'DOG', 1),
       (7, 'Палкан', 7, 'Овчарка', true, 'DOG', 1),
       (8, 'Граф', 10, 'Лайка', true, 'DOG', 1);
SELECT SETVAL('animal_id_seq', (SELECT MAX(id) FROM animal));

INSERT INTO owner (telegram_id, username, firstname, lastname, phone_number)
VALUES (111111, 'Ivan_Ivanov', 'Иван', 'Иванов', '+79115648532'),
       (333333, 'Alex', 'Алексей', 'Алексеев', '+79053648512'),
       (444444, 'Semen_Semenov', 'Семен', 'Семенов', '+79308493265'),
       (555555, 'Svetlaya', 'Света', 'Светикова', '+79913665885');

INSERT INTO adaptation (id, start_date, end_date, comment, adaptation_status, animal_id, owner_id)
VALUES (1, '2023-09-09', '2023-10-09', null, 'IN_PROGRESS', 3, 111111),
       (2, '2023-09-09', '2023-10-09', null, 'IN_PROGRESS', 7, 444444),
       (3, '2023-09-09', '2023-10-09', 'забыла отправить отчет', 'IN_PROGRESS', 2, 555555);
SELECT SETVAL('adaptation_id_seq', (SELECT MAX(id) FROM adaptation));

INSERT INTO report (id, report_message, photo, photo_url, date, adaptation_id)
VALUES (1, 'Забился в угол, не выходит есть и пить', '1-20230909.jpg', 'http://localhost:8081/api/v1/reports/1/photo', '2023-09-09', 1),
       (2, 'Все хорошо, обоссал все углы(', '2-20230909.jpg', 'http://localhost:8081/api/v1/reports/2/photo', '2023-09-09', 2),
       (3, 'Уже лучше, вышел поесть и попить 1 разок)', '1-20230910.jpg', 'http://localhost:8081/api/v1/reports/3/photo', '2023-09-10', 1),
       (4, 'Все ок, по прежнему ссыт в углы(((', '2-20230910.jpg', 'http://localhost:8081/api/v1/reports/4/photo', '2023-09-10', 2);
SELECT SETVAL('report_id_seq', (SELECT MAX(id) FROM report));

INSERT INTO users (telegram_id, username, firstname, lastname, phone_number, shelter_id)
VALUES (111111, 'Ivan_Ivanov', 'Иван', 'Иванов', '+79115648532', 1),
       (222222, 'Petr_Petrov', 'Петр', 'Петров', '+79256279136', 1),
       (333333, 'Alex', 'Алексей', 'Алексеев', '+79053648512', 1),
       (444444, 'Semen_Semenov', 'Семен', 'Семенов', '+79308493265', 1),
       (555555, 'Svetlaya', 'Света', 'Светикова', '+79913665885', 1),
       (666666, 'Dimon', 'Дмитрий', 'Дмитриев', '+79913665885', 1),
       (777777, 'Romashka', 'Роман', 'Романов', '+79913665885', 1);

INSERT INTO volunteer (telegram_id, username, firstname, lastname, shelter_id)
VALUES (666666, 'Dimon', 'Дмитрий', 'Дмитриев', 1),
       (777777, 'Romashka', 'Роман', 'Романов', 1);