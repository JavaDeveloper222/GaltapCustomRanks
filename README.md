GaltapCustomRanks - это простое API для работы с рангами. С его помощью вы можете создавать и управлять рангами в вашей игре. 
---
#### Все методы, доступные в API:
```Java
Rank getRankFromPlayer(String uuid) - метод позволяет получить ранг игрока по его UUID.
Rank getRankFromId(String id) - этот метод позволяет получить ранг по его уникальному ID.
Rank getRankFromPosition(int position) - с помощью этого метода можно получить ранг по его позиции.
Rank getRankByPrice(int price) - метод позволяет получить ближайший по цене ранг.
Rank getRankByBlock(int blockCount) - с помощью этого метода можно получить ближайший по количеству блоков ранг.
List<Rank> getAllRank() - метод возвращает список всех доступных рангов.
void updateRank(Player player) - с помощью этого метода можно обновить ранг игрока.
```
Класс методов можно получить так:
```Java
RankManager rankManager = GaltapCustomRanks.getInstance().getRankManager();
```
---
##### Чтобы начать использовать API, вам необходимо подключить зависимость Maven. Для этого добавьте следующий код в ваш файл pom.xml:

repasitory:
```Java
<repositories>
    <repository>
        <id>jitpack.io</id>
      <url>https://jitpack.io</url>
    </repository>
</repositories>
```

dependency:
```Java
<dependency>
    <groupId>com.github.JavaDeveloper222</groupId>
    <artifactId>GaltapCustomRanks</artifactId>
    <version>v.2.0.1</version>
</dependency>
```
