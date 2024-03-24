# Nexign-Project 
Перед вами небольшое приложение, которое:
1. Генерирует cdr-файлы
2. Сохраняет в cdr-файлы в БД
3. Формирует udr-файлы
4. Предоставляет представление udr-файлов в табличном формате(в cmd) и формате json-файлов
5. В табличном форате возможно выбрать каким образом будет предоставлен ответ из БД
## Как запустить
Для запуска потребуется Docker. Запустите следующую команду:
```
docker run --name nexign -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=nexign -d -p 5432:5432 postgres:16
```
Далее запуск происходит через:
```
public class NexignProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(NexignProjectApplication.class, args);
	}
}
```

В результате работы вы получите:
Файлы cdr


![image](https://github.com/Discovery19/Nexign-Project/assets/112725051/7f63d7f6-a8ba-4d72-a6b2-ba3afdcdf687)


![image](https://github.com/Discovery19/Nexign-Project/assets/112725051/5a339cdc-3c7a-40ce-9c85-b2009c75867d)

Предоставление в табличном формате (если вы не меняли начальные условия)

![image](https://github.com/Discovery19/Nexign-Project/assets/112725051/46717d5e-3319-4b9a-852f-04ce28412f0f)

Файлы udr

![image](https://github.com/Discovery19/Nexign-Project/assets/112725051/e8cbfac5-35fd-4271-83f0-57fe0a0daff5)

![image](https://github.com/Discovery19/Nexign-Project/assets/112725051/2604afc2-5bc6-452d-bcd5-f30d620dc114)


В приложении присутствует Sheduler, который каждую минуту будет генерировать новые значения и новые файлы. 
В классе AppScheduler вы можете добавить начальные значения в generateReport() для изменения представления в cmd

```
public class AppScheduler {
    //
    @Scheduled(fixedDelayString = "#{@scheduler.interval()}")
    public void update() {
        //
        reports.generateReport();
        log.info("generate report in cmd");
    }
}
```

В классе CdrFileGenerator можно изменить данные статические переменные для получения различных результатов

```
    private static final int YEAR = 2024;
    private static final int NUM_MONTH = 4;//длительность по месяцам
    private static final int NUM_CALLS = 10;//максимальное кол-во вызовов для абонента
```

Данный массив является заглушкой под БД клиентов.

```
    private final String[] subscribers = {
            "79876543221", "79996667755"
            , "79998887766", "79995556677"
            , "79994445588", "79000445588"
            , "89994445588", "89000445588"
            , "99994445588", "99000445588"};
```




