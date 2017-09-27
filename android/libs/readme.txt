百度地图Android SDK自v2.3.0起，采用可定制的形式为您提供开发包，当前开发包包含如下功能:
基础地图：地图显示、操作、个性化地图切换和各种覆盖物图层：
检索功能：POI检索、地理编码、路径规划等；
LBS云检索：基于LBS云的周边、城市内、区域、详情检索；
计算工具：调启百度地图客户端导航、poi检索、poi点全景展示、调启Web App导航、计算距离等；
周边雷达：包含位置信息上传和检索周边相同应用的用户位置信息功能；

基础地图：地图显示、操作和各种覆盖物图层：

检索功能：POI检索、地理编码、路径规划等；

LBS云检索：基于LBS云的周边、城市内、区域、详情检索；

计算工具：调启百度地图客户端导航、调启Web App导航、计算距离等；

周边雷达：包含位置信息上传和检索周边相同应用的用户位置信息功能；

------------------------------------------------------------------------------------------------

Android 地图 SDK v4.0.0是适用于Android系统移动设备的矢量地图开发包

------------------------------------------------------------------------------------------------
注意：为了给用户提供更安全的服务，Android SDK自v2.1.3版本开始采用了全新的Key验证体系。因此，当您选择使用v2.1.3及之后版本的SDK时，需要到新的Key申请页面进行全新Key的申请，申请及配置流程请参考开发指南的对应章节。（选择使用v2.1.2及之前版本SDK的开发者，申请密钥（Key） 的方式不变）
------------------------------------------------------------------------------------------------
地图SDK功能介绍（全功能开发包）：

地图：
	提供地图（2D、3D、室内图）的展示和缩放、平移、旋转、改变视角等地图操作；个性化地图展示、个性化地图与普通地图类型动态切换
POI检索：
	可根据关键字，对POI数据进行周边、区域和城市内三种检索，支持poi室内检索；
地理编码：
	提供地理坐标和地址之间相互转换的能力；
线路规划：
	支持公交信息查询、公交换乘查询、驾车线路规划和步行路径检索；
覆盖物：
	提供多种地图覆盖物（瓦片图层、自定义标注、几何图形、文字绘制、地形图图层、热力图图层等），满足开发者的各种需求；
定位：
	采用多种定位模式，使用定位SDK获取位置信息，使用地图SDK我的位置图层进行位置展示；
离线地图：
	支持使用离线地图，节省用户流量，同时为用户带来更好的地图体验；
调启百度地图：
	利用SDK接口，直接在本地打开百度地图客户端或WebApp，实现地图功能。
周边雷达：
	利用周边雷达功能，开发者可在App内低成本、快速实现查找周边使用相同App的用户位置的功能。
LBS云检索：
	支持用户检索存储在LBS云内的自有POI数据，并展示；
特色功能：
	提供短串分享、Place详情检索、热力图等特色功能，帮助开发者搭建功能更加强大的应用；
------------------------------------------------------------------------------------------------

【新版提示】

	
1、自v3.6.0起，官网不再支持地图离线包下载，所以SDK去掉“手动导入离线包接口”，SDK在线下载离线包接口仍维持不变。	

2、因为新版采用新的分包形式，旧包无法与新包同时混用，请将之前所有旧包（so和jar）全部替换为新包。	

3、自V3.6.0起，Android SDK使用新的矢量地图样式，地图显示更加清新，和百度地图客户端保持一致。
	

4、自V3.6.0起，原内置覆盖物相关类代码开源（OverlayManager/PoiOverlay/TransitRouteOverlay/WalkingRouteOverlay/BusLineOverlay）,源码可在BaiduMapsApiDemo工程中找到。

	
5、自V3.6.0起，Android SDK采用功能包拆分的形式，

其中:
baidumapapi_base_vX_X_X.jar和libBaiduMapSDK_base_vX_X_X.so为基础包，使用地图、检索、云检索、工具、周边雷达中任何一功能都必须包含；


baidumapapi_map_vX_X_X.jar和libBaiduMapSDK_map_vX_X_X.so为地图功能包；
baidumapapi_search_vX_X_X.jar和libBaiduMapSDK_search_vX_X_X.so为检索功能包；


baidumapapi_cloud_vX_X_X.jar和libBaiduMapSDK_cloud_vX_X_X.so为云检索功能包；
baidumapapi_util_vX_X_X.jar和libBaiduMapSDK_util_vX_X_X.so为工具功能包；


baidumapapi_radar_vX_X_X.jar和libBaiduMapSDK_radar_vX_X_X.so为周边雷达工具包；


如果您从http://lbsyun.baidu.com/sdk/download这里下载的开发包，提供给您的将所有jar打包成BaiduLBS_Android.jar。native动态库so的形式不变。


较之v3.7.3，升级功能：


【 新 增 】


 [ 基 础 地 图 ]


1、 适配Android Wear，新增WearMapView

2、 新增室内地图及标注展示

    BaiduMap新增接口 showMapIndoorPoi(boolean isShow), 设置室内图标注是否显示，默认为TRUE

    BaiduMap新增接口 setIndoorEnable(boolean isShow), 设置室内图是否显示，默认为FALSE

    BaiduMap新增接口 switchBaseIndoorMapFloor(String strFloor, String strID), 切换室内图楼层

    BaiduMap新增接口 setOnBaseIndoorMapListener(OnBaseIndoorMapListener listener), 设置进出室内图回调

    新增室内图信息类 MapBaseIndoorMapInfo


3、 新增普通地图与个性化地图切换
	
    MapView/TextureMapView/WearMapView 新增接口 setMapCustomEnable(boolean customMapStyleEnable), 设置个性化地图样式是否生效

4、 新增个性化地图json文件检查及解析错误时的日志提醒


【 优 化 】

1、 点聚合开源包新增点击marker的回调

2、 删除了一些权限问题导致的日志打印

【 变 更 】

1、新增setViewpadding方法，map设置Padding切换时，底图中心点不变更，废弃setPadding

 [ 检 索 功 能 ]

1、新增室内POI检索
  
    PoiSearch新增接口 searchPoiIndoor(PoiIndoorOption option), 发起poi室内检索
    
    新增室内POI信息类 PoiIndoorInfo
	
    新增室内poi检索参数类 PoiIndoorOption

    新增室内POI搜索结果类 PoiIndoorResult

    OnGetPoiSearchResultListener 回调接口新增onGetPoiIndoorResult(PoiIndoorResult result) 获取poi室内检索结果信息

2、驾车新增3个属性：打车费用信息、拥堵指数、红绿灯个数

3、公交线路检索新增2个属性：参考票价、上下线行信息

 [ 计 算 工 具 ]

1、新增调起客户端全景功能

    BaiduMapPoiSearch 新增接口openBaiduMapPanoShow(String uid, Context context) 用于调起客户端的poi全景展示    

         
【 Bug 修 复 】 
         
1、  针对不同平台下加载so文件失败，增加重新拷贝so的容错机制。

2、  修复在instant run开启后，加载assets失败导致MapView空指针的问题

3、  修复native层偶现的crash问题

4、  修复TextureMapview偶现空指针问题



