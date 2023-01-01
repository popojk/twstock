#台股小幫手

##前言

`台股小幫手`是一款協助查詢台股大盤及個股多種數據資料、以及串接line bot傳送到價通知及股價查
詢的app，使用前後端分離的架構，前端使用React框架，後端使用Spring Boot框架，並串連
MYSQL資料庫與Redis緩存，最後使用Docker打包並部署至Azure雲服務．
app透過後端串接finmind與富果API取得台股大盤與個股相關資料並轉換為前端需要的資料格式，
當使用者註冊app帳號並加入台股小幫手line官方帳號後，可以使用網頁或line設定個股到價通知
，也可以使用line查詢即時股價並建立個股觀察清單(指令如line官方帳號說明)，後端將透過
line設定，linebot傳送相關通知．

##專案網址
- 項目網址:
- github clone:

##項目功能介紹


##使用技術
###後端:

| 技術                | 用途                |
| ------------------ | ------------------- |
|Spring Boot         |後端框架              |
|Spring Security     |後端JWT權限控管        |
|JPA                 |資料庫ORM             |
|MySql               |後端RDBS資料庫         |
|Redis               |緩存快速取得股票代號/名稱 |
|Swagger             |撰寫API文件             |
|Lombok              |簡化物件工具             |
|Feign               |後端傳送http request至line bot API |
|nginx               |前後端服務反向代理       |
|Docker              |容器化服務並上傳雲服務          |

###前端:
| 技術                | 用途                |
| ------------------ | ------------------- |
|React               |前端框架              |
|Spring Boot         |後端框架              |

##連結網址

##台股相關資訊查詢

前端透過TaStatsController各個API endpoint取得台股相關數據，TaStatsController

