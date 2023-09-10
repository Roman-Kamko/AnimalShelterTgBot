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

    public static final String DOG_TRANSPORTATION = """
            1. Используйте специальный переносной контейнер или клетку для перевозки собаки. Убедитесь, что контейнер достаточно прочный и безопасный для вашей собаки.
            2. Предоставьте достаточно места в контейнере для собаки, чтобы она могла полностью повернуться, встать и лечь.
            3. Пометьте контейнер собаки яркими и четкими ярлыками, включая информацию о вашем имени, контактных данных и адресе назначения.
            4. Установите вентиляцию в контейнере, чтобы обеспечить достаточное поступление свежего воздуха.
            5. Предоставьте своей собаке достаточное количество воды во время перевозки. Используйте капельницу или поилку, чтобы предотвратить проливание воды.
            6. Перед транспортировкой убедитесь, что ваша собака получила все необходимые прививки и имеет микрочип с актуальной информацией.
            7. Если вы путешествуете на автомобиле, закрепите контейнер собаки безопасно на заднем сиденье или в багажном отсеке. Никогда не оставляйте собаку свободно бегать в машине.
            8. Если вы летите на самолете, обратитесь к авиакомпании, чтобы узнать их правила и требования для перевозки собаки. Обратите внимание на размеры контейнера и необходимые документы.
            9. Перед путешествием позаботьтесь о том, чтобы собака была удобно и знакома с контейнером. Проведите несколько тренировочных сессий, где собака может привыкнуть к контейнеру и чувствовать себя комфортно в нем.
            10. Во время путешествия регулярно проверяйте состояние и комфортность собаки. Периодически предлагайте ей воду и убедитесь, что она чувствует себя хорошо.
            Запомните, что безопасность и комфорт вашей собаки являются приоритетом при транспортировке. Если у вас возникают дополнительные вопросы или сомнения, обратитесь к ветеринару или специалисту по поведению животных для получения более подробных рекомендаций.
            """;
    public static final String HOME_FOR_PUPPY = """
            Вот список рекомендаций по обустройству дома для щенка:
            1. Создайте безопасное пространство для щенка, где он может отдыхать и спокойно играть. Изолируйте опасные зоны, например, проведите барьеры, чтобы предотвратить доступ к кабелям, опасным химикатам или лестницам.
            2. Разместите в доме мягкое и удобное место для отдыха щенка, такую как подушка или специальная кровать для собаки. Обеспечьте щенку свое место, где он будет чувствовать себя комфортно.
            3. Подготовьте все необходимые предметы ухода для щенка, такие как миски для еды и воды, игрушки, поилка, намордник (если требуется) и гигиенические принадлежности.
            4. Закройте доступ к опасным предметам, таким как электрические провода, ядовитые растения, мусорные контейнеры и т.д. Убедитесь, что все опасные предметы хранятся в недоступных для щенка местах.
            5. Обучите щенка использовать специальное место для туалета. Разместите тренировочные подгузники или специальный лоток для щенка. Поощряйте щенка, когда он правильно использует место для туалета.
            6. Подготовьте игрушки для щенка, чтобы он мог заняться активными играми и развлечениями. Игрушки помогут щенку развивать зубы, укреплять мышцы и предотвращать скучность.
            7. Регулярно проводите социализацию щенка, позволяя ему встречаться с другими собаками и людьми. Это поможет щенку адаптироваться к новым ситуациям и быть общительным.
            8. Создайте график регулярного кормления и прогулок для щенка. Установите регулярные время для еды, сна и обучения, чтобы помочь щенку развиваться и поддерживать режим.
            9. Установите ограничения для щенка и обучите его базовым командам, таким как "сидеть", "лежать", "фас" и "место". Это поможет установить правила и контроль над поведением щенка.
            10. Помните, что терпение и любовь - ключевые составляющие воспитания щенка. Будьте готовы потратить время на тренировку, игры и уход за щенком, чтобы он стал счастливым и здоровым членом вашей семьи.
            Помните, что каждый щенок уникален, и его потребности могут немного отличаться. Важно наблюдать за щенком, обеспечивать ему заботу и любовь, чтобы он мог адаптироваться и расти в безопасной и комфортной среде.
            """;
    public static final String HOME_FOR_ADULT_DOG = """
            Вот список рекомендаций по обустройству дома для взрослой собаки:
            1. Создайте специальное место для отдыха собаки, где она может комфортно лежать и спать. Разместите мягкую и удобную кровать или подстилку в тихом и спокойном месте.
            2. Предоставьте достаточно пространства для активности и игр. Убедитесь, что в доме или на участке есть свободное пространство, где собака может бегать, прыгать и играть.
            3. Создайте безопасное окружение в доме. Уберите опасные предметы, химические вещества и растения, которые могут быть вредными для собаки. Обратите внимание на провода, чтобы собака не могла их погрызть.
            4. Установите миски для еды и воды в удобном для собаки месте. Обеспечьте постоянный доступ к чистой и свежей воде, чтобы собака всегда могла утолить жажду.
            5. Предоставьте достаточно игрушек для занятий собаки. Разнообразие игрушек поможет удовлетворить потребности в жевании, тренировке и развлечениях.
            6. Установите в доме зону для туалета собаки, если она не выходит на улицу для справления нужд. Используйте тренировочные подгузники или специальный лоток для этой цели.
            7. Обеспечьте достаточно упражнений и прогулок для собаки. Регулярные прогулки и физическая активность помогут собаке оставаться здоровой и счастливой.
            8. Обучите собаку командам и установите правила поведения в доме. Это поможет собаке понять, что от нее ожидается, и поддерживать хорошую дисциплину.
            9. Позаботьтесь о гигиене собаки. Регулярно чистите ее миски, место отдыха и игрушки. Проводите регулярные купания и уход за шерстью, зубами и глазами собаки.
            10. Дайте своей собаке достаточно внимания, любви и заботы. Проводите время с ней, играйте, тренируйте и развлекайтесь вместе. Будьте внимательны к ее потребностям и заботьтесь о ее благополучии.
            Помните, что каждая собака уникальна, и ее потребности могут немного отличаться. Важно наблюдать за своей собакой, обеспечивать ей заботу и любовь, чтобы она чувствовала себя счастливой и комфортной в своем доме.
            """;

    public static final String HOME_FOR_DISABLED_DOG = """
            Вот список рекомендаций по обустройству дома для собаки с ограниченными возможностями:
            1. Создайте безбарьерное пространство для собаки. Уберите пороги, ковры или другие препятствия, чтобы собака могла свободно передвигаться по дому без помех.
            2. Предоставьте дополнительную поддержку и комфорт для собаки. Используйте специальные коврики или коврики с антискользящим покрытием, чтобы предотвратить скольжение и обеспечить лучшую поддержку при ходьбе.
            3. Разместите миски для еды и воды на уровне собаки, чтобы ей было удобно достигать их без излишнего напряжения.
            4. Создайте специальное место для отдыха с мягкой и удобной кроватью, чтобы собака могла отдыхать и спать без дискомфорта.
            5. Установите поручни или ручки в местах, где собака может нуждаться в поддержке при подъеме или спуске по лестнице или на другие поверхности.
            6. Уберите опасные предметы и препятствия, которые могут быть опасными или вызывать травмы для собаки с ограниченными возможностями. Обратите внимание на провода, ядовитые растения или острые предметы.
            7. Обеспечьте доступ к месту для туалета, который будет удобен для собаки. Если собака не может выходить на улицу, рассмотрите возможность использования специального лотка или подгузника.
            8. Подумайте о доступе к игрушкам и занятиям для собаки. Разместите игрушки и другие предметы, с которыми она любит играть, в местах, где она может достигнуть их без труда.
            9. Обеспечьте регулярные физические упражнения и прогулки, адаптированные под возможности собаки. Это может включать короткие прогулки или физические упражнения, которые подходят для ее состояния.
            10. Оказывайте дополнительную заботу, внимание и любовь собаке с ограниченными возможностями. Будьте терпеливы и готовы адаптировать свои действия и среду, чтобы собаке было комфортно и удобно.
            Важно индивидуально адаптировать среду и заботу для собаки с ограниченными возможностями в соответствии с ее конкретными потребностями. Обратитесь к ветеринару или специалисту по поведению животных, чтобы получить более подробные рекомендации, основанные на конкретных особенностях вашей собаки.
            """;

    public static final String CYNOLOGIST_ADVICE = """
            Вот несколько советов кинолога по первичному общению с соб
            1. Показывайте спокойствие и уверенность: Собаки чувствуют энергию и настроение людей. Поэтому важно проявлять спокойствие и уверенность при общении с собакой. Избегайте нервозности или страха, так как это может вызвать беспокойство у собаки.
            2. Устанавливайте правила и границы: Собаки нуждаются в ясных правилах и границах, чтобы понимать, что от них ожидается. Установите основные правила в начале и следуйте им последовательно. Например, запрещайте собаке прыгать на людей или кусать во время игры.
            3. Используйте положительное подкрепление: При обучении и общении с собакой, используйте положительное подкрепление, такое как похвала, ласка или маленькая вкусная награда. Это поможет собаке понять, что она делает правильно и будет стимулировать ее желание повторять желаемое поведение.
            4. Изучайте язык тела собаки: Собаки общаются главным образом через язык тела. Изучите основные сигналы и выражения собак, чтобы понимать их настроение и коммуницировать с ними правильно. Например, узнайте, что означает хвост собаки, позиции ушей и мимика лица.
            5. Подходите к собаке снизу и с боку: Когда вы встречаете новую собаку, подходите к ней снизу и с боку, а не сверху или спереди. Это поможет создать большую комфортность и уверенность у собаки.
            6. Не навязывайте физический контакт: Некоторые собаки могут быть неуверенными или недоверчивыми. Не навязывайте им физический контакт, если они не желают его. Дайте собаке время и пространство для адаптации и установления доверия.
            7. Изучайте особенности породы: Каждая порода имеет свои особенности и потребности. Изучите особенности породы вашей собаки, чтобы лучше понимать ее поведение и потребности. Это поможет вам лучше общаться и заботиться о вашей собаке.
            8. Будьте терпеливы и последовательны: Обучение и установление связи с собакой - это процесс, требующий времени и терпения. Будьте последовательными в своих тренировках и ожидайте, что собаке понадобится время, чтобы освоить новые команды и привычки.
            9. Играйте и проводите время вместе: Игра и активное времяпрепровождение - это отличный способ укрепления связи с собакой. Найдите игры и активности, которые приносят радость вашей собаке, и проводите время вместе.
            10. Обращайтесь за помощью, если ваша собака без настроения несколько дней!
            """;
    public static final String PROVEN_CYNOLOGISTS = """
            Проверенные кинологи для дальнейшего обращения:
                        
            1. Ликаренко Вячеслав Витальевич
            Услуги: кинолог, дрессировщик, зоопсихология.
            Опыт работы кинолог — 2012–2023 гг. — 11 лет.
            Стоимость услуг — от 500 ₽.
                        
            2. Чилоч Ольга Алексеевна
            Услуги: кинолог, грумер, дрессировщик.
            Опыт работы кинолог — 2021–2023 гг. — 2 года.
            Стоимость услуг — от 100 ₽.
                        
            3. Балтрушайтите Саманта Роландовна
            Услуги: кинолог, зооняня, дрессировщик.
            Опыт работы кинолог — 2020–2023 гг. — 3 года.
            Стоимость услуг — от 300 ₽.
                        
            4. Куртукова Анастасия Сергеевна
            Услуги: кинолог, зоопсихология.
            Опыт работы кинолог — 2015–2023 гг. — 8 лет.
            Стоимость услуг — от 1 300 ₽.
                        
            5. Чернова Татьяна Владимировна
            Услуги: кинолог, грумер, дрессировщик.
            Опыт работы кинолог — 2020–2023 гг. — 3 года.
            Стоимость услуг — уточняется при заказе.
            """;

    public static final String DATING_RULES = """
            1. Когда вы выбрали из нашего каталога животное, с которым хотели бы познакомиться, можно приехать в приют и  встретиться с будущим питомцем. Кошек можно навестить прямо в домиках, а вот с собачкой лучшей выйти на прогулку в парк и познакомиться там ближе с характером животного.
            2. Ведите себя спокойно. Не нужно разговаривать слишком громко и размахивать руками. Следите за поведением детей: звуки могут напугать кошек, которые и без того находятся в стрессе.
            3. Помойте руки или обработайте их. Не открывайте клетки с кошками без разрешения сотрудника приюта даже в том случае, когда животное очень хочет пообщаться.
            4. Не тревожьте кошку которая отдыхает или боится. Не стучите по клетке, стараясь привлечь ее внимание.
            5. Не берите животное на руки без разрешения сотрудника. Не стоит этого делать и в том случае, когда животное спит, ест, занято другими делами или не расположено к общению. Не кормите животных без разрешения.
            6. После того, как вы поймете, что именно этого котика или собачку вы хотите забрать из приюта, необходимо пообщаться с работником приюта или с курирующим волонтером, чтобы узнать про особенности поведения, содержания и питания животного, а также рассказать в каких условиях в дальнейшем будет жить питомец. Далее пройти собеседование с волонтерами и оформить договор.
            """;
    public static final String DOC_LIST = """
            1.Заполнить анкету будущего владельца животного.
            2.Не забыть свой паспорт.
            3.Заключить договор передачи животного.
            """;
    public static final String DENIAL_REASONS = """
            1. Сотрудники и волонтеры проводят собеседования с желающими взять бездомное животное, запрашивают документы, могут проверить ваше жилье и заглянуть к соседям. Если они сочтут будущие условия содержания животного не подходящими, вам откажут.
            2. Учреждение не отдает животных молодым парам, одиноким пожилым людям и в семьи с младенцами.
            3. Приют откажет, если собаку хотят использовать в качестве сторожа или посадить на цепь.
            4. Также приют не отдает животное, если его хотят забрать, чтобы вручить в качестве подарка. На это есть множество обоснованных причин, самая суть которых - избежания стресса для питомца, который вновь по вине хозяина может оказаться в приюте или, что еще хуже, на улице.
            """;
    public static final String CAT_TRANSPORTATION = """
            1. Переноска для кошек. Она будет выполнять роль временного убежища для кошки. В переноску можно поместить знакомое кошке полотенце, маленькое покрывало. Размер переноски должен позволять кошке сидеть, разворачиваться и вытягивать лапы. От стресса кошка может описаться, поэтому дно переноски следует застелить влаговпитывающей пеленкой.
            2. Пища и питье. В длительном путешествии кошке необходимо обеспечить доступ к еде и чистой воде. Для снижения неприятных ощущений от траспортировки можно дать кошке любимое лакомство. Кошка может отказаться от корма, в стрессовой ситуации животные могут не пить в течении дня, не есть до трех дней. Это нормально и принуждать животное к приему пищи и питья не нужно.
            3. Лоток. Если путешествие займет больше суток, кошке нужно обеспечить доступ к ее лотку. Лоток нужно установить в безопасном тихом месте, он должен содержать малую часть использованного наполнителя, чтобы кошка легко узнала свой запах.
            4. Движение. Каждые три часа кошке рекомендуется предоставлять возможность размять лапы. Если кошка спокойна, ее можно выпустить из переноски погулять по салону. На улице необходимо использовать шлейку, иначе кошка может испугаться неожиданных звуков и убежать.
            5. Успокоительное. За 10-15 дней до поездки рекомендуется давать кошке успокоительные средства (капли, таблетки) на основе растительных компонентов. Они не содержат вредных и сильнодействующих веществ, поэтому эффект накопительный.
            6. Ветеринарный паспорт. В настоящий момент для транспортировки домашних животных в пределах России на автомобиле или поезде ветеринарный паспорт не требуется. Он необходим для перемещения на самолете. В любом случае, в дорогу лучше взять медицинскую карточку или ветеринарный паспорт для продолжения ветеринарного обслуживания на новом месте.
            """;
    public static final String HOME_FOR_KITTY = """
            1. Список предметов, которые вам обязательно потребуются в первое время:
            Переноска
            Корм
            Лоток
            Наполнитель
            Миски (для еды и воды)
            Когтеточка
            Домик.
            2. Не забудьте поставить сетки на окна и убрать с пола провода, чтобы котенок не решил поточить о них зубы и не пострадал, а вы не остались без связи. Ваше жилье должно стать безопасным для нового члена семьи.
            3. Домашнему любимцу не должно быть скучно. Особенно это актуально для котят. Больше всего кошки любят четыре вещи — играть, отдыхать, прятаться и карабкаться вверх. Чтобы обезопасить и питомца, и свои вещи, стоит оборудовать специальный кошачий комплекс с крытой норкой наверху и когтеточкой внизу(домик). Возможно, вашему котенку понравится спать в маленькой корзинке.
            4. Следует помнить, что в природе кошки охотятся на небольших птиц, грызунов и рептилий. Поэтому надо заранее обезопасить этих питомцев, поскольку животное не упустит возможности попытаться их поймать. С крупными грызунами, такими как морские свинки, кошки уживаются мирно. Это касается и таких попугаев, как амазон, какаду, ара. Аквариум кошка также не оставит в покое и попытается выловить рыбок.
            """;
    public static final String HOME_FOR_ADULT_CAT = """
            Прежде чем забрать питомца к себе, нужно подготовить квартиру к новому жильцу.
            1. Список предметов, которые вам обязательно потребуются в первое время:
            Переноска
            Корм
            Лоток
            Наполнитель
            Миски (для еды и воды)
            Когтеточка
            Домик.
            2. Не забудьте поставить сетки на окна и убрать с пола провода, чтобы котенок не решил поточить о них зубы и не пострадал, а вы не остались без связи. Ваше жилье должно стать безопасным для нового члена семьи.\s
            3. Домашнему любимцу не должно быть скучно. Больше всего кошки любят четыре вещи — играть, отдыхать, прятаться и карабкаться вверх. Чтобы обезопасить и питомца, и свои вещи, стоит оборудовать специальный кошачий комплекс с крытой норкой наверху и когтеточкой внизу(домик).
            4. Следует помнить, что в природе кошки охотятся на небольших птиц, грызунов и рептилий. Поэтому надо заранее обезопасить этих питомцев, поскольку животное не упустит возможности попытаться их поймать. С крупными грызунами, такими как морские свинки, кошки уживаются мирно. Это касается и таких попугаев, как амазон, какаду, ара. Аквариум кошка также не оставит в покое и попытается выловить рыбок.
            """;
    public static final String HOME_FOR_DISABLED_CAT = """
            До того, как вы привезете нового члена семьи в дом, позаботьтесь о том, чтобы купить средство от блох и клещей, зоошампунь, и корм. До того, как купить еду для питомца посоветуйтесь с сотрудниками приюта или куратором животного. Возможно, у него назначен специализированный корм или имеется аллергия.  Не самые дешевые готовые корма (дешевые в основном могут только навредить) сбалансированы и снабжены необходимыми витаминами и минералами в отличие от домашней еды.
            1. Список предметов, которые вам обязательно потребуются в первое время:
            Переноска
            Корм
            Лоток
            Наполнитель
            Миски (для еды и воды)
            Когтеточка
            Домик.
            2. В зависимости от особенностей инвалидности кошки, ей могут быть нужны специальные лекарства или приспособления. Волонтеры расскажут вам об особенностях ухода вашего питомца. Скорее всего, почувствовав себя дома, питомец пойдет на поправку и будет способен выполнять все действия, как и обычные кошки: кушать, играть, ходить в лоток.
            3. Не забудьте поставить сетки на окна и убрать с пола провода, чтобы котенок не решил поточить о них зубы и не пострадал, а вы не остались без связи. Ваше жилье должно стать безопасным для нового члена семьи.
            4. Домашнему любимцу не должно быть скучно. Больше всего кошки любят четыре вещи — играть, отдыхать, прятаться и карабкаться вверх. Чтобы обезопасить и питомца, и свои вещи, стоит оборудовать специальный кошачий комплекс с крытой норкой наверху и когтеточкой внизу(домик).
            5. Следует помнить, что в природе кошки охотятся на небольших птиц, грызунов и рептилий. Поэтому надо заранее обезопасить этих питомцев, поскольку животное не упустит возможности попытаться их поймать. С крупными грызунами, такими как морские свинки, кошки уживаются мирно. Это касается и таких попугаев, как амазон, какаду, ара. Аквариум кошка также не оставит в покое и попытается выловить рыбок.
            """;

}