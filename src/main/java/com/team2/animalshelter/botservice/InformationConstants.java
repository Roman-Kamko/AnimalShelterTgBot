package com.team2.animalshelter.botservice;

public class InformationConstants {

    public static final String FAQ_COMMAND = """
            Вот что я могу тебе рассказать:
                        
                Общие темы:
                /datingRules - правила знакомства с животным до того, как забрать его из приюта
                /docList - список документов, необходимых для того, чтобы взять животное из приюта
                /denialReasons - список причин, почему могут отказать в опеке над животным
                
                Кошки:
                /catTransportation - список рекомендаций по транспортировке кошки
                /homeForKitty - список рекомендаций по обустройству дома для котенка
                /homeForAdultCat - список рекомендаций по обустройству дома для взрослой кошки
                /homeForDisabledCat - список рекомендаций по обустройству дома для кошки с ограниченными возможностями
                
                Собаки:
                /dogTransportation - список рекомендаций по транспортировке собаки
                /homeForPuppy - список рекомендаций по обустройству дома для щенка
                /homeForAdultDog - список рекомендаций по обустройству дома для взрослой собаки
                /homeForDisabledDog - список рекомендаций по обустройству дома для собаки с ограниченными возможностями
                /cynologistAdvice - советы кинолога по первичному общению с собакой
                /provenCynologists - проверенные кинологи для дальнейшего обращения
            """;
    public static final String UNKNOWN_COMMAND = "Данная команда пока не поддерживается \uD83D\uDE14";

    public static final String CALL_VOLUNTEER = "Запрос принят, как только освободится волонтер, он сразу же Вам ответит \uD83D\uDE0A";

    public static final String DOG_TRANSPORTATION = "1. Используйте специальный переносной контейнер или клетку для перевозки собаки. Убедитесь, что контейнер достаточно прочный и безопасный для вашей собаки.\n" +
            "\n" +
            "2. Предоставьте достаточно места в контейнере для собаки, чтобы она могла полностью повернуться, встать и лечь.\n" +
            "\n" +
            "3. Пометьте контейнер собаки яркими и четкими ярлыками, включая информацию о вашем имени, контактных данных и адресе назначения.\n" +
            "\n" +
            "4. Установите вентиляцию в контейнере, чтобы обеспечить достаточное поступление свежего воздуха.\n" +
            "\n" +
            "5. Предоставьте своей собаке достаточное количество воды во время перевозки. Используйте капельницу или поилку, чтобы предотвратить проливание воды.\n" +
            "\n" +
            "6. Перед транспортировкой убедитесь, что ваша собака получила все необходимые прививки и имеет микрочип с актуальной информацией.\n" +
            "\n" +
            "7. Если вы путешествуете на автомобиле, закрепите контейнер собаки безопасно на заднем сиденье или в багажном отсеке. Никогда не оставляйте собаку свободно бегать в машине.\n" +
            "\n" +
            "8. Если вы летите на самолете, обратитесь к авиакомпании, чтобы узнать их правила и требования для перевозки собаки. Обратите внимание на размеры контейнера и необходимые документы.\n" +
            "\n" +
            "9. Перед путешествием позаботьтесь о том, чтобы собака была удобно и знакома с контейнером. Проведите несколько тренировочных сессий, где собака может привыкнуть к контейнеру и чувствовать себя комфортно в нем.\n" +
            "\n" +
            "10. Во время путешествия регулярно проверяйте состояние и комфортность собаки. Периодически предлагайте ей воду и убедитесь, что она чувствует себя хорошо.\n" +
            "\n" +
            "Запомните, что безопасность и комфорт вашей собаки являются приоритетом при транспортировке. Если у вас возникают дополнительные вопросы или сомнения, обратитесь к ветеринару или специалисту по поведению животных для получения более подробных рекомендаций.";
    public static final String HOME_FOR_PUPPY = "Вот список рекомендаций по обустройству дома для щенка:\n" +
            "\n" +
            "1. Создайте безопасное пространство для щенка, где он может отдыхать и спокойно играть. Изолируйте опасные зоны, например, проведите барьеры, чтобы предотвратить доступ к кабелям, опасным химикатам или лестницам.\n" +
            "\n" +
            "2. Разместите в доме мягкое и удобное место для отдыха щенка, такую как подушка или специальная кровать для собаки. Обеспечьте щенку свое место, где он будет чувствовать себя комфортно.\n" +
            "\n" +
            "3. Подготовьте все необходимые предметы ухода для щенка, такие как миски для еды и воды, игрушки, поилка, намордник (если требуется) и гигиенические принадлежности.\n" +
            "\n" +
            "4. Закройте доступ к опасным предметам, таким как электрические провода, ядовитые растения, мусорные контейнеры и т.д. Убедитесь, что все опасные предметы хранятся в недоступных для щенка местах.\n" +
            "\n" +
            "5. Обучите щенка использовать специальное место для туалета. Разместите тренировочные подгузники или специальный лоток для щенка. Поощряйте щенка, когда он правильно использует место для туалета.\n" +
            "\n" +
            "6. Подготовьте игрушки для щенка, чтобы он мог заняться активными играми и развлечениями. Игрушки помогут щенку развивать зубы, укреплять мышцы и предотвращать скучность.\n" +
            "\n" +
            "7. Регулярно проводите социализацию щенка, позволяя ему встречаться с другими собаками и людьми. Это поможет щенку адаптироваться к новым ситуациям и быть общительным.\n" +
            "\n" +
            "8. Создайте график регулярного кормления и прогулок для щенка. Установите регулярные время для еды, сна и обучения, чтобы помочь щенку развиваться и поддерживать режим.\n" +
            "\n" +
            "9. Установите ограничения для щенка и обучите его базовым командам, таким как \"сидеть\", \"лежать\", \"фас\" и \"место\". Это поможет установить правила и контроль над поведением щенка.\n" +
            "\n" +
            "10. Помните, что терпение и любовь - ключевые составляющие воспитания щенка. Будьте готовы потратить время на тренировку, игры и уход за щенком, чтобы он стал счастливым и здоровым членом вашей семьи.\n" +
            "\n" +
            "Помните, что каждый щенок уникален, и его потребности могут немного отличаться. Важно наблюдать за щенком, обеспечивать ему заботу и любовь, чтобы он мог адаптироваться и расти в безопасной и комфортной среде.";
    public static final String HOME_FOR_ADULT_DOG = "Вот список рекомендаций по обустройству дома для взрослой собаки:\n" +
            "\n" +
            "1. Создайте специальное место для отдыха собаки, где она может комфортно лежать и спать. Разместите мягкую и удобную кровать или подстилку в тихом и спокойном месте.\n" +
            "\n" +
            "2. Предоставьте достаточно пространства для активности и игр. Убедитесь, что в доме или на участке есть свободное пространство, где собака может бегать, прыгать и играть.\n" +
            "\n" +
            "3. Создайте безопасное окружение в доме. Уберите опасные предметы, химические вещества и растения, которые могут быть вредными для собаки. Обратите внимание на провода, чтобы собака не могла их погрызть.\n" +
            "\n" +
            "4. Установите миски для еды и воды в удобном для собаки месте. Обеспечьте постоянный доступ к чистой и свежей воде, чтобы собака всегда могла утолить жажду.\n" +
            "\n" +
            "5. Предоставьте достаточно игрушек для занятий собаки. Разнообразие игрушек поможет удовлетворить потребности в жевании, тренировке и развлечениях.\n" +
            "\n" +
            "6. Установите в доме зону для туалета собаки, если она не выходит на улицу для справления нужд. Используйте тренировочные подгузники или специальный лоток для этой цели.\n" +
            "\n" +
            "7. Обеспечьте достаточно упражнений и прогулок для собаки. Регулярные прогулки и физическая активность помогут собаке оставаться здоровой и счастливой.\n" +
            "\n" +
            "8. Обучите собаку командам и установите правила поведения в доме. Это поможет собаке понять, что от нее ожидается, и поддерживать хорошую дисциплину.\n" +
            "\n" +
            "9. Позаботьтесь о гигиене собаки. Регулярно чистите ее миски, место отдыха и игрушки. Проводите регулярные купания и уход за шерстью, зубами и глазами собаки.\n" +
            "\n" +
            "10. Дайте своей собаке достаточно внимания, любви и заботы. Проводите время с ней, играйте, тренируйте и развлекайтесь вместе. Будьте внимательны к ее потребностям и заботьтесь о ее благополучии.\n" +
            "\n" +
            "Помните, что каждая собака уникальна, и ее потребности могут немного отличаться. Важно наблюдать за своей собакой, обеспечивать ей заботу и любовь, чтобы она чувствовала себя счастливой и комфортной в своем доме.";
    public static final String HOME_FOR_DISABLED_DOG = "Вот список рекомендаций по обустройству дома для собаки с ограниченными возможностями:\n" +
            "\n" +
            "1. Создайте безбарьерное пространство для собаки. Уберите пороги, ковры или другие препятствия, чтобы собака могла свободно передвигаться по дому без помех.\n" +
            "\n" +
            "2. Предоставьте дополнительную поддержку и комфорт для собаки. Используйте специальные коврики или коврики с антискользящим покрытием, чтобы предотвратить скольжение и обеспечить лучшую поддержку при ходьбе.\n" +
            "\n" +
            "3. Разместите миски для еды и воды на уровне собаки, чтобы ей было удобно достигать их без излишнего напряжения.\n" +
            "\n" +
            "4. Создайте специальное место для отдыха с мягкой и удобной кроватью, чтобы собака могла отдыхать и спать без дискомфорта.\n" +
            "\n" +
            "5. Установите поручни или ручки в местах, где собака может нуждаться в поддержке при подъеме или спуске по лестнице или на другие поверхности.\n" +
            "\n" +
            "6. Уберите опасные предметы и препятствия, которые могут быть опасными или вызывать травмы для собаки с ограниченными возможностями. Обратите внимание на провода, ядовитые растения или острые предметы.\n" +
            "\n" +
            "7. Обеспечьте доступ к месту для туалета, который будет удобен для собаки. Если собака не может выходить на улицу, рассмотрите возможность использования специального лотка или подгузника.\n" +
            "\n" +
            "8. Подумайте о доступе к игрушкам и занятиям для собаки. Разместите игрушки и другие предметы, с которыми она любит играть, в местах, где она может достигнуть их без труда.\n" +
            "\n" +
            "9. Обеспечьте регулярные физические упражнения и прогулки, адаптированные под возможности собаки. Это может включать короткие прогулки или физические упражнения, которые подходят для ее состояния.\n" +
            "\n" +
            "10. Оказывайте дополнительную заботу, внимание и любовь собаке с ограниченными возможностями. Будьте терпеливы и готовы адаптировать свои действия и среду, чтобы собаке было комфортно и удобно.\n" +
            "\n" +
            "Важно индивидуально адаптировать среду и заботу для собаки с ограниченными возможностями в соответствии с ее конкретными потребностями. Обратитесь к ветеринару или специалисту по поведению животных, чтобы получить более подробные рекомендации, основанные на конкретных особенностях вашей собаки.";
    public static final String CYNOLOGIST_ADVICE = "Вот несколько советов кинолога по первичному общению с собакой:\n" +
            "\n" +
            "1. Показывайте спокойствие и уверенность: Собаки чувствуют энергию и настроение людей. Поэтому важно проявлять спокойствие и уверенность при общении с собакой. Избегайте нервозности или страха, так как это может вызвать беспокойство у собаки.\n" +
            "\n" +
            "2. Устанавливайте правила и границы: Собаки нуждаются в ясных правилах и границах, чтобы понимать, что от них ожидается. Установите основные правила в начале и следуйте им последовательно. Например, запрещайте собаке прыгать на людей или кусать во время игры.\n" +
            "\n" +
            "3. Используйте положительное подкрепление: При обучении и общении с собакой, используйте положительное подкрепление, такое как похвала, ласка или маленькая вкусная награда. Это поможет собаке понять, что она делает правильно и будет стимулировать ее желание повторять желаемое поведение.\n" +
            "\n" +
            "4. Изучайте язык тела собаки: Собаки общаются главным образом через язык тела. Изучите основные сигналы и выражения собак, чтобы понимать их настроение и коммуницировать с ними правильно. Например, узнайте, что означает хвост собаки, позиции ушей и мимика лица.\n" +
            "\n" +
            "5. Подходите к собаке снизу и с боку: Когда вы встречаете новую собаку, подходите к ней снизу и с боку, а не сверху или спереди. Это поможет создать большую комфортность и уверенность у собаки.\n" +
            "\n" +
            "6. Не навязывайте физический контакт: Некоторые собаки могут быть неуверенными или недоверчивыми. Не навязывайте им физический контакт, если они не желают его. Дайте собаке время и пространство для адаптации и установления доверия.\n" +
            "\n" +
            "7. Изучайте особенности породы: Каждая порода имеет свои особенности и потребности. Изучите особенности породы вашей собаки, чтобы лучше понимать ее поведение и потребности. Это поможет вам лучше общаться и заботиться о вашей собаке.\n" +
            "\n" +
            "8. Будьте терпеливы и последовательны: Обучение и установление связи с собакой - это процесс, требующий времени и терпения. Будьте последовательными в своих тренировках и ожидайте, что собаке понадобится время, чтобы освоить новые команды и привычки.\n" +
            "\n" +
            "9. Играйте и проводите время вместе: Игра и активное времяпрепровождение - это отличный способ укрепления связи с собакой. Найдите игры и активности, которые приносят радость вашей собаке, и проводите время вместе.\n" +
            "\n" +
            "10. Обращайтесь за помощью, если ваша собока без настроения несколько дней!";
    public static final String PROVEN_CYNOLOGISTS = "Проверенные кинологи для дальнейшего обращения:\n" +
            "1. Ликаренко Вячеслав Витальевич\n" +
            "Услуги: кинолог, дрессировщик, зоопсихология.\n" +
            "Опыт работы кинолог — 2012–2023 гг. — 11 лет.\n" +
            "Стоимость услуг — от 500 ₽.\n" +
            "\n" +
            "2. Чилоч Ольга Алексеевна\n" +
            "Услуги: кинолог, грумер, дрессировщик.\n" +
            "Опыт работы кинолог — 2021–2023 гг. — 2 года.\n" +
            "Стоимость услуг — от 100 ₽.\n" +
            "\n" +
            "3. Балтрушайтите Саманта Роландовна\n" +
            "Услуги: кинолог, зооняня, дрессировщик.\n" +
            "Опыт работы кинолог — 2020–2023 гг. — 3 года.\n" +
            "Стоимость услуг — от 300 ₽.\n" +
            "\n" +
            "4. Куртукова Анастасия Сергеевна\n" +
            "Услуги: кинолог, зоопсихология.\n" +
            "Опыт работы кинолог — 2015–2023 гг. — 8 лет.\n" +
            "Стоимость услуг — от 1 300 ₽.\n" +
            "\n" +
            "5. Чернова Татьяна Владимировна\n" +
            "Услуги: кинолог, грумер, дрессировщик.\n" +
            "Опыт работы кинолог — 2020–2023 гг. — 3 года.\n" +
            "Стоимость услуг — уточняется при заказе.";
}
