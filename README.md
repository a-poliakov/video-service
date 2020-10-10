## Проектная работа по курсу Highload Architect (otus.ru): потоковый сервис видео-связи с постобработкой

Сервис состоит из серверного бекенда, десктопного приложения и мобильных клиентов (ios и android).

Десктопное клиентское приложение:
<table>
    <tr>
        <td><img src="imgs/screenshot_1.png" width="240" height="320"></td>
        <td><img src="imgs/screenshot_2.png" width="320" height="240"></td>
    </tr>
    <tr>
        <td><img src="imgs/screenshot_3.png" width="240" height="320"></td>
        <td><img src="imgs/screenshot_4.png" width="240" height="320"></td>
     </tr>
</table>


+ [Архитектура решения](#Architecture);
+ [Описание процесса установки](#Install);
+ [Полезные ресурсы](#Resources);

## <a name="Architecture"></a> Архитектура решения:

Подробное описание архитектуры решения в [презентации](final_project_presentation.pdf).

Верхнеуровневая архитектура:

![](imgs/VideoCalls%20Services%20Structure.png)

Работа вычислителей, обрабатывающих фрейме видео-потока:

![](imgs/VideoProcessor%20services%20cluster.png)

Внутреннее устройство сервисов обработки видео-потока:

![](imgs/Video%20Calls%20Stream%20Pipeline%20Structure.png)

## <a name="Install"></a> Описание процесса установки:

Сервисы бекенда можно запускать в docker ([образы на docker-hub]()).

Для поднятия окружения следует воспользоваться docker-compose файлом [environment.yml](./environment.yml).

Клиентское приложение представляет собой исполняемый jar-файл (необходимо наличие установленной JRE 8)
на компьютере пользователя.

## <a name="Resources"></a> Полезные ресурсы: