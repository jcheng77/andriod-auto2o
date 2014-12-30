/*
 * Copyright (C) 2010 The MobileSecurePay Project
 * All right reserved.
 * author: shiqun.shi@alipay.com
 * 
 *  提示：如何获取安全校验码和合作身份者id
 *  1.用您的签约支付宝账号登录支付宝网站(www.alipay.com)
 *  2.点击“商家服务”(https://b.alipay.com/order/myorder.htm)
 *  3.点击“查询合作者身份(pid)”、“查询安全校验码(key)”
 */

package com.alipay.android.msp;

//
// 请参考 Android平台安全支付服务(msp)应用开发接口(4.2 RSA算法签名)部分，并使用压缩包中的openssl RSA密钥生成工具，生成一套RSA公私钥。
// 这里签名时，只需要使用生成的RSA私钥。
// Note: 为安全起见，使用RSA私钥进行签名的操作过程，应该尽量放到商家服务器端去进行。
public final class Keys {

	//合作身份者id，以2088开头的16位纯数字
	public static final String DEFAULT_PARTNER = "2088511841967594";

	//收款支付宝账号
	public static final String DEFAULT_SELLER = "auto2o@outlook.com";

	//商户私钥，自助生成
	public static final String PRIVATE = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAKl5EBi60MoIkSQmZyIljQquA45JhAkamGC2nwN9dZBnqcWmiPh5M1//kQ/6XB4eUVOTh4pHCxoNkuT19F/6WRNF1QNP3fiGt/q50Jwgmet+4GsUTWFD1wSThS6eCOkLQCppgT5e5kUddvQESV299YgeOSEIyVY0eka+dlHlTOSpAgMBAAECgYAiN384fv+IyxIC6n4INuyzK08se0tdSzFY1YZlff8umL9+WJFvGYl16HOxdisDKbvh0/eZw55KNFNkRwSAHFu/ZAboQnuUOLwHw615WgoK/gVx4henohVGP1HJpeg/WSgZbU/3sAloWrD/ovU8vV9KdH+9kT0aTBQ5acZfkXZoAQJBAOHq4dvYPeKVZMEeDxgJTEPQmg3kVNa3/H6tX7IaILCgLS1TknOGKErAVxm+OpZvz9hWJyTk+wa2COcxKJjO11ECQQDAChYgra+5LgeVOs/AX52G5Mpg7LrTVVoGI3mlUW2gv9K4vxCGIWuuAVMqhs83E6csnZir91NDJ4Bu9H8z7BHZAkALpQGzRTgbX7vrwFLi2EfYDv6BzM0arC0VknYmRfZ9ZCQv++jGj5mwEK3so8N9UZITAo3N9weBVwyqbfw7tB2hAkBq+vN6vuc+lNrakkm71EgwJnJrblVhd5HQC6EvrF4TB+l+y8mLv0B6Tfijnzf+aa9elmi/m+dBaNcOeJwIM8F5AkBBYxuyA602joM7AzqB7p5raJwCNM3fvG2FCzZBvhXSXqfqohjiMhkZHfoM+5xPpVA+aIO3hi2zJWklSaX3MA//";

	public static final String PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";

}
