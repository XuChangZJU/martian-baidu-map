package org.lovebing.reactnative.baidumap;

import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiSortType;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.utils.CoordinateConverter;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.ReadableMap;

import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;
/**
 * Created by lovebing on 2016/10/28.
 */
public class GeolocationModule extends BaseModule
    implements BDLocationListener, OnGetGeoCoderResultListener, OnGetPoiSearchResultListener {

    private LocationClient locationClient;
    private static GeoCoder geoCoder;
    private static PoiSearch mPoiSearch;

    public GeolocationModule(ReactApplicationContext reactContext) {
        super(reactContext);
        context = reactContext;
    }

    public String getName() {
        return "BaiduGeolocationModule";
    }


    private void initLocationClient() {
        LocationClientOption option = new LocationClientOption();
        option.setCoorType("bd09ll");
        option.setIsNeedAddress(true);
        option.setIsNeedAltitude(true);
        option.setIsNeedLocationDescribe(true);
        option.setOpenGps(true);
        locationClient = new LocationClient(context.getApplicationContext());
        locationClient.setLocOption(option);
        Log.i("locationClient", "locationClient");
        locationClient.registerLocationListener(this);
    }
     private WritableArray poiListToArray(List<PoiInfo> list){
        WritableArray poiList = Arguments.createArray();
        for (PoiInfo p : list){
            WritableMap poi = Arguments.createMap();

            poi.putString("name", p.name);
            poi.putString("uid", p.uid);
            poi.putString("address", p.address);
            poi.putString("city", p.city);
            poi.putString("phoneNum", p.phoneNum);
            poi.putString("postCode", p.postCode);
            poi.putInt("type", p.type != null ? p.type.getInt() : 0);
            //poi.putInt("type", p.type.getInt());


            WritableMap location = Arguments.createMap();
            location.putDouble("latitude", p.location.latitude);
            location.putDouble("longitude", p.location.longitude);
            poi.putMap("location", location);

            poiList.pushMap(poi);
        }
        return poiList;
    }
    /**
     *
     * @return
     */
    protected GeoCoder getGeoCoder() {
        if(geoCoder != null) {
            geoCoder.destroy();
        }
        geoCoder = GeoCoder.newInstance();
        geoCoder.setOnGetGeoCodeResultListener(this);
        return geoCoder;
    }
    /**
     *
     * @return
     */
    protected PoiSearch getPoiSearch() {
        if(mPoiSearch != null) {
            mPoiSearch.destroy();
        }

        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(this);
        return mPoiSearch;
    }

    /**
     *
     * @param sourceLatLng
     * @return
     */
    protected LatLng getBaiduCoorFromGPSCoor(LatLng sourceLatLng) {
        CoordinateConverter converter = new CoordinateConverter();
        converter.from(CoordinateConverter.CoordType.GPS);
        converter.coord(sourceLatLng);
        LatLng desLatLng = converter.convert();
        return desLatLng;

    }

   private LatLng readLatLng(ReadableMap pos) {
        return new LatLng(pos.getDouble("latitude"), pos.getDouble("longitude"));
    }

    @ReactMethod
    public void getCurrentPosition() {
        if(locationClient == null) {
            initLocationClient();
        }
        Log.i("getCurrentPosition", "getCurrentPosition");
        locationClient.start();
    }
    @ReactMethod
    public void geocode(String city, String addr) {
        getGeoCoder().geocode(new GeoCodeOption()
                .city(city).address(addr));
    }

    @ReactMethod
    public void reverseGeoCode(double lat, double lng) {
        getGeoCoder().reverseGeoCode(new ReverseGeoCodeOption()
                .location(new LatLng(lat, lng)));
    }

    @ReactMethod
    public void reverseGeoCodeGPS(double lat, double lng) {
        getGeoCoder().reverseGeoCode(new ReverseGeoCodeOption()
                .location(getBaiduCoorFromGPSCoor(new LatLng(lat, lng))));
    }

    @ReactMethod
    public void poiSearch(ReadableMap loc, ReadableMap map) {

        PoiNearbySearchOption option = new PoiNearbySearchOption();
        option.location(readLatLng(loc));
        if (map.hasKey("keyword")){
            option.keyword(map.getString("keyword"));
        }
        if (map.hasKey("sortMode")) {
            option.sortType("nearToFar".equals(map.getString("sortMode")) ? PoiSortType.distance_from_near_to_far : PoiSortType.comprehensive);
        }
        if (map.hasKey("radius")){
            option.radius(map.getInt("radius"));
        }
        if (map.hasKey("pageIndex")){
            option.pageNum(map.getInt("pageIndex"));
        }
        if (map.hasKey("pageCapacity")){
            option.pageCapacity(map.getInt("pageCapacity"));
        }
        getPoiSearch().searchNearby(option);
    }

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        WritableMap params = Arguments.createMap();
        params.putDouble("latitude", bdLocation.getLatitude());
        params.putDouble("longitude", bdLocation.getLongitude());
        params.putDouble("direction", bdLocation.getDirection());
        params.putDouble("altitude", bdLocation.getAltitude());
        params.putDouble("radius", bdLocation.getRadius());
        params.putString("address", bdLocation.getAddrStr());
        params.putString("countryCode", bdLocation.getCountryCode());
        params.putString("country", bdLocation.getCountry());
        params.putString("province", bdLocation.getProvince());
        params.putString("cityCode", bdLocation.getCityCode());
        params.putString("city", bdLocation.getCity());
        params.putString("district", bdLocation.getDistrict());
        params.putString("street", bdLocation.getStreet());
        params.putString("streetNumber", bdLocation.getStreetNumber());
        params.putString("buildingId", bdLocation.getBuildingID());
        params.putString("buildingName", bdLocation.getBuildingName());
        Log.i("onReceiveLocation", "onGetCurrentLocationPosition");
        sendEvent("onGetCurrentLocationPosition", params);
        locationClient.stop();
    }

    @Override
    public void onGetGeoCodeResult(GeoCodeResult result) {
        WritableMap params = Arguments.createMap();
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            params.putInt("errcode", -1);
        }
        else {
            params.putDouble("latitude",  result.getLocation().latitude);
            params.putDouble("longitude",  result.getLocation().longitude);
        }
        sendEvent("onGetGeoCodeResult", params);
    }

    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
        WritableMap params = Arguments.createMap();
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            params.putInt("errcode", -1);
        }
        else {
            ReverseGeoCodeResult.AddressComponent addressComponent = result.getAddressDetail();
            params.putString("address", result.getAddress());
            params.putString("province", addressComponent.province);
            params.putString("city", addressComponent.city);
            params.putString("district", addressComponent.district);
            params.putString("street", addressComponent.street);
            params.putString("streetNumber", addressComponent.streetNumber);
            params.putArray("pois", poiListToArray(result.getPoiList()));

        }
        sendEvent("onGetReverseGeoCodeResult", params);
    }

    @Override
    public void onGetPoiResult(PoiResult result) {
     Toast.makeText(this.getReactApplicationContext().getApplicationContext(), "onGetPoiResult",
                  Toast.LENGTH_SHORT).show();
        WritableMap params = Arguments.createMap();
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            params.putInt("errcode", -1);
        }
        else {
            params.putInt("totalPages", result.getTotalPageNum());
            params.putInt("totalCount", result.getTotalPoiNum());
            params.putArray("pois", poiListToArray(result.getAllPoi()));
        }
        sendEvent("onGetPoiResult", params);

    }
      @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
            }

      @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {
            }
}

