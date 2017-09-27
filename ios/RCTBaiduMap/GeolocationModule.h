//
//  GoelocationModule.h
//  RCTBaiduMap
//
//  Created by lovebing on 2016/10/28.
//  Copyright © 2016年 lovebing.org. All rights reserved.
//

#ifndef GeolocationModule_h
#define GeolocationModule_h


#import <BaiduMapAPI_Location/BMKLocationService.h>

#import "BaseModule.h"
#import "RCTBaiduMapViewManager.h"

@interface GeolocationModule : BaseModule <BMKGeoCodeSearchDelegate, BMKPoiSearchDelegate>
+ (NSArray *) _convertPoiList: (NSArray*) list;
+ (NSDictionary *)_convertLocation:(BMKUserLocation *)userLocation;
+ (NSDictionary *)_convertRegion:(BMKCoordinateRegion)region;
+ (NSDictionary *)_convertAddress: (BMKAddressComponent*) address;

@end

#endif /* GeolocationModule_h */
