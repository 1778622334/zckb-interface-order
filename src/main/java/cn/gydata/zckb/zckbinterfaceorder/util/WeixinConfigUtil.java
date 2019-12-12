package cn.gydata.zckb.zckbinterfaceorder.util;

public class WeixinConfigUtil {

    public static final String replayErrorMsg = "error";

    //创建二维码
    public static final String createQrcodeUrl = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=%s";

    //获取access_token
    public static final String getAccessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";

    //二维码地址
    public static final String qrcodeUrl = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=%s";

    //获取微信用户信息
    public static final String getWxUserUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=%s&openid=%s&lang=zh_CN";

    //发送客服文本消息
    public static final String sendCustomMsgUrl = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=%s";

    //医生登录二维码内容
    public static final String loginQrcode = "login@-@%s";

    public static final String getJsapiTicketUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=%s&type=jsapi";


    //发送模板消息url
    public static final String sendTemplateUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=%s";


    //网页用户授权url(静默)
    public static final String wxAuthorizeUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";

    //获取网页用户信息
    public static final String getH5UserByCode = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";

    //微信支付
    public static final String wxPayUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    //患者关注模板key
    public static final String patTemp = "wx_patient_follow";

    //二维码获取地址
    public static final String qrcodeDomain = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=TICKET";

    //微信退款地址
    public static final String refundUrl = "https://api.mch.weixin.qq.com/secapi/pay/refund";

    //设置菜单
    public static final String setMenu = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=%s";

}
