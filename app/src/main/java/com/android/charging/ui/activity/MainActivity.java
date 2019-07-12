package com.android.charging.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.android.charging.R;
import com.android.charging.bean.LocationBean;
import com.android.charging.bean.MenuBean;
import com.android.charging.manager.ActManager;
import com.android.charging.ui.adapter.InfoWinAdapter;
import com.android.charging.ui.adapter.RecycleMainAdapter;
import com.android.charging.ui.dialog.CommomDialog;
import com.android.charging.ui.view.PagerEnabledSlidingPaneLayout;
import com.android.charging.util.EdittorUtil;
import com.android.charging.util.StatusBarUtil;
import com.android.charging.util.ToastUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bertsir.zbar.QrConfig;
import cn.bertsir.zbar.QrManager;

public class MainActivity extends BaseActivity implements RecycleMainAdapter.Lister, LocationSource, AMapLocationListener {

    @BindView(R.id.btn_scan)
    public Button btnScan;
    @BindView(R.id.layout_menu)
    public LinearLayout layoutMenu;
    @BindView(R.id.recyclerView)
    public RecyclerView mRecyclerView;
    @BindView(R.id.iv_head_photo)
    public ImageView ivHeadPhoto;
    @BindView(R.id.tv_user_id)
    public TextView tvUserId;
    @BindView(R.id.tv_user_phone)
    public TextView tvUserPhone;
    @BindView(R.id.img_call_phone)
    public ImageView imgCallPhone;      //拨打客服
    @BindView(R.id.img_charging)
    public ImageView imgCharging;       //附近电桩
    @BindView(R.id.img_user)
    public ImageView imgUser;
    @BindView(R.id.sliding_pan)
    public PagerEnabledSlidingPaneLayout slidingPan;
    @BindView(R.id.map_view)
    public MapView mapView;
    @BindView(R.id.img_near_act)
    public ImageView imgNearAct;
    @BindView(R.id.img_meal)
    public ImageView imgMeal;
    @BindView(R.id.ed_search)
    public EditText edSearch;
    @BindView(R.id.img_search)
    public ImageView imgSearch;
    @BindView(R.id.rl_title)
    public RelativeLayout rlTitle;

    private RecycleMainAdapter adapter;
    private AMap aMap = null;
    private AMapLocationClient mLocationClient = null;      //声明mLocationOption对象，定位参数
    public AMapLocationClientOption mLocationOption = null; //声明mListener对象，定位监听器
    private OnLocationChangedListener mListener = null;     //标识，用于判断是否只显示一次定位信息和用户重新定位
    private MyLocationStyle myLocationStyle;//定位蓝点

    private boolean isFirstLoc = true;
    private int REQUESTCODE = 101;

    public static Intent newIntent(Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView(savedInstanceState);
    }

    private void initView(Bundle savedInstanceState) {
        SlidingPaneLayout.LayoutParams linearParams = (SlidingPaneLayout.LayoutParams) layoutMenu.getLayoutParams();
        linearParams.width = ActManager.getAppManager().getWidth(this) * 1 / 2;
        layoutMenu.setLayoutParams(linearParams);

        /**
         * 设置地图
         */
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        init();

        /**
         * 动态设置用户信息按钮位置
         */
        int height = StatusBarUtil.getStatusBarHeight(this);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(30, height + 40, 40, 0);
        rlTitle.setLayoutParams(layoutParams);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecycleMainAdapter(this, this);
        mRecyclerView.setAdapter(adapter);
        List<MenuBean> list = new ArrayList<>();
        list.add(new MenuBean("我的钱包", R.mipmap.icon_my_burse));
        list.add(new MenuBean("最新消息", R.mipmap.icon_new_msg));
        list.add(new MenuBean("充电订单", R.mipmap.icon_order));
        list.add(new MenuBean("交易记录", R.mipmap.icon_deal));
        list.add(new MenuBean("我的卡管理", R.mipmap.icon_my_card));
        list.add(new MenuBean("充电帮助", R.mipmap.icon_help));
        list.add(new MenuBean("意见反馈", R.mipmap.icon_feedback));
        list.add(new MenuBean("关于我们", R.mipmap.icon_regard));
        list.add(new MenuBean("退出登录", R.mipmap.icon_exit_login));
        adapter.setData(list);

        Glide.with(this).load(R.mipmap.icon_head_photo)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(ivHeadPhoto);
    }

    /**
     * 初始化AMap对象
     */
    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
        }

        //设置地图的放缩级别
        /*aMap.moveCamera(CameraUpdateFactory.zoomTo(100));*/
        // 设置定位监听
        aMap.setLocationSource(this);
        // 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap.setMyLocationEnabled(true);
        // 设置定位的类型为定位模式，有定位、跟随或地图根据面向方向旋转几种
        aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
        aMap.getUiSettings().setRotateGesturesEnabled(false);//禁止地图旋转手势.
        //蓝点初始化
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.getUiSettings().setMyLocationButtonEnabled(false);//设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）默认执行此种模式。
        myLocationStyle.showMyLocation(true);
        aMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                int markerId = Integer.parseInt(marker.getId().substring(6));
                Log.e(TAG, "当前选中的marker:" + markerId);
                marker.setTitle(marker.getTitle());
                return false;
            }
        });
        aMap.setInfoWindowAdapter(new InfoWinAdapter());
        location();
    }

    public void addMarker() {
        List<LocationBean> list = new ArrayList<>();
        list.add(new LocationBean("该充电桩有1个空闲位置,1个正在使用,0个发生故障", 113.5603523254, 34.7947042766));
        list.add(new LocationBean("该充电桩有2个空闲位置,2个正在使用,0个发生故障", 113.5879898071, 34.7884309326));
        list.add(new LocationBean("该充电桩有3个空闲位置,3个正在使用,0个发生故障", 113.5590648651, 34.8291639087));
        list.add(new LocationBean("该充电桩有4个空闲位置,4个正在使用,0个发生故障", 113.5366630554, 34.8114778403));
        for (LocationBean bean : list) {
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.title(bean.title);
            markerOptions.position(new LatLng(bean.latitude, bean.longitude));
            markerOptions.visible(true);
            BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.icon_tab));
            markerOptions.icon(bitmapDescriptor);
            aMap.addMarker(markerOptions);
        }
    }

    private void location() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(this);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为Hight_Accuracy高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(false);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //显示实时路况图层，aMap是地图控制器对象。
        aMap.setTrafficEnabled(true);
        //启动定位
        mLocationClient.startLocation();
    }


    @Override
    public void onClick(int position) {
        switch (position) {
            case 0:
                startActivity(MyWalletActivity.newIntent(this));
                break;
            case 1:
                startActivity(NewMsgActivity.newIntent(this));
                break;
            case 2:
                startActivity(OrderActivity.newIntent(this));
                break;
            case 3:

                break;
            case 4:

                break;
            case 5:
                startActivity(HelpActivity.newIntent(this));
                break;
            case 6:
                startActivity(FeedBackActivity.newIntent(this));
                break;
            case 7:
                startActivity(AboutActivity.newIntent(this));
                break;
            case 8:
                startActivity(LoginActivity.newIntent(this));
                this.finish();
                break;
        }
    }

    /**
     * 跳转到扫码界面扫码
     */
    private void goScan() {
        QrConfig qrConfig = new QrConfig.Builder()
                .setDesText("扫码充电")//扫描框下文字
                .setShowDes(false)//是否显示扫描框下面文字
                .setShowLight(false)//显示手电筒按钮
                .setShowTitle(true)//显示Title
                .setShowAlbum(true)//显示从相册选择按钮
                .setCornerColor(Color.parseColor("#029EE9"))//设置扫描框颜色
                .setLineColor(Color.parseColor("#029EE9"))//设置扫描线颜色
                .setLineSpeed(QrConfig.LINE_MEDIUM)//设置扫描线速度
                .setScanType(QrConfig.TYPE_QRCODE)//设置扫码类型（二维码，条形码，全部，自定义，默认为二维码）
                .setScanViewType(QrConfig.SCANVIEW_TYPE_QRCODE)//设置扫描框类型（二维码还是条形码，默认为二维码）
                .setCustombarcodeformat(QrConfig.BARCODE_EAN13)//此项只有在扫码类型为TYPE_CUSTOM时才有效
                .setPlaySound(true)//是否扫描成功后bi~的声音
                .setIsOnlyCenter(false)//是否只识别框中内容(默认为全屏识别)
                .setTitleText("扫码充电")//设置Tilte文字
                .setIdentify_type(QrConfig.TYPE_ONCE)//1:连续扫码  2:单次扫码
                .setTitleBackgroudColor(Color.parseColor("#029EE9"))//设置状态栏颜色
                .setTitleTextColor(Color.WHITE)//设置Title文字颜色
                .create();
        QrManager.getInstance().init(qrConfig).startScan(MainActivity.this, new QrManager.OnScanResultCallback() {
            @SuppressLint("MissingPermission")
            @Override
            public void onScanSuccess(int code, String result) {
                Log.e(TAG, "result:" + result + ",code:" + code);
                ToastUtil.showShortToast(MainActivity.this, "result:" + result);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 101:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    goScan();
                } else {
                    ToastUtil.showShortToast(this, "你拒绝了权限申请，可能无法打开相机扫码哟！");
                }
                break;
            default:
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        if (mLocationClient != null) {
            mLocationClient.stopLocation();//停止定位
            mLocationClient.onDestroy();//销毁地图
        }
        mLocationClient = null;
        ActManager.getAppManager().finishActivity(this);
    }

    @OnClick({R.id.iv_head_photo,
            R.id.btn_scan,
            R.id.img_call_phone,
            R.id.img_charging,
            R.id.img_user,
            R.id.rl_main,
            R.id.img_meal,
            R.id.img_near_act,
            R.id.img_search,
            R.id.ed_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_head_photo:
                startActivity(PersonInfoActivity.newIntent(MainActivity.this));
                break;
            case R.id.btn_scan:
                if (slidingPan.isOpen()) {
                    slidingPan.closePane();
                    return;
                }
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, REQUESTCODE);
                } else {
                    //扫码
                    goScan();
                }
                break;
            case R.id.img_call_phone:
                new CommomDialog(this, R.style.dialog_commom, "拨打电话:400-900-3683", new CommomDialog.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog, boolean confirm) {
                        if (confirm) {
                            // TODO: 2019\6\21 0021  去拨打电话
                            dialog.dismiss();
                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "400-900-3683"));
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        } else {
                            dialog.dismiss();
                        }
                    }
                }).setTitle("客户服务").show();
                break;
            case R.id.img_charging:
                startActivity(NearChargingActivity.newIntent(MainActivity.this));
                break;
            case R.id.img_meal:
                startActivity(MealActivity.newIntent(MainActivity.this));
                break;
            case R.id.img_near_act:
                startActivity(NearActActivity.newIntent(MainActivity.this));
                break;
            case R.id.img_user:
                EdittorUtil.hideInput(this);
                if (slidingPan.isOpen()) {
                    slidingPan.closePane();
                } else {
                    slidingPan.openPane();
                }
                break;
            case R.id.img_search://搜索

                break;
            case R.id.ed_search:
                if (slidingPan.isOpen()) {
                    slidingPan.closePane();
                    return;
                }
                break;
        }
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                aMapLocation.getLocationType();
                //获取当前定位结果来源，如网络定位结果，详见官方定位类型表
                aMapLocation.getLatitude();
                //获取纬度
                aMapLocation.getLongitude();
                //获取经度
                aMapLocation.getAccuracy();
                //获取精度信息
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(aMapLocation.getTime());
                df.format(date);
                //定位时间
                aMapLocation.getAddress();
                //地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                aMapLocation.getCountry();
                //国家信息
                aMapLocation.getProvince();
                //省信息
                aMapLocation.getCity();
                //城市信息
                aMapLocation.getDistrict();
                //城区信息
                aMapLocation.getStreet();
                //街道信息
                aMapLocation.getStreetNum();
                //街道门牌号信息
                aMapLocation.getCityCode();
                //城市编码
                aMapLocation.getAdCode();
                // 地区编码
                //3.实现高德地图定位功能，并在控制台打印坐标信息；
                Log.d(TAG, "详细定位信息 ：" + aMapLocation.getAddress());
                Log.d(TAG, "全称定位信息 ：" + aMapLocation.getCountry() + "" + aMapLocation.getProvince() + "" + aMapLocation.getCity() + "" +
                        aMapLocation.getDistrict() + "" + aMapLocation.getStreet() + "" + aMapLocation.getStreetNum());
                Log.d(TAG, "经度：" + aMapLocation.getLongitude() + "  纬度：" + aMapLocation.getLatitude());

                // 如果不设置标志位，此时再拖动地图时，它会不断将地图移动到当前的位置
                if (isFirstLoc) {
                    //设置缩放级别
                    /*aMap.moveCamera(CameraUpdateFactory.zoomTo(30));*/
                    //将地图移动到定位点
                    aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude())));
                    //点击定位按钮 能够将地图的中心移动到定位点
                    Log.e(TAG, "定位成功,移动到当前位置");
                    mListener.onLocationChanged(aMapLocation);
                    isFirstLoc = false;
                    addMarker();
                }
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e(TAG, "location Error, ErrCode: " + aMapLocation.getErrorCode() + " , errInfo: " + aMapLocation.getErrorInfo());
            }
        }
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
    }

    @Override
    public void deactivate() {
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }
        mLocationClient = null;
        mListener = null;
    }
}