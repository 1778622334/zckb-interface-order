package cn.gydata.zckb.zckbinterfaceorder.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 以post或get方式调用远程方法
 *
 * @author leoliang
 *
 */
@Slf4j
public class HttpClientLocalUtils {


	// 设置请求和传输超时时间
	public static RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(20000).setConnectTimeout(5000).build();

	/**
	 *
	 * @param obj 请求类型，'get'为HttpGet，'post'为HttpPost
	 * @param requestUrl 请求的URL
	 * @param param 请求的参数以key-value的形式存放
	 * @param headParam 请求的头信息
	 * @return
	 * @throws IOException
	 */
	public static String getTelnetData(Object obj, String requestUrl, Map<String, Object> param, Map<String, Object> headParam) {
		String responseStr = null;
		final HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		final CloseableHttpClient httpclient = httpClientBuilder.build();

		/**
		 * 远程方法为get请求
		 */
		if (obj instanceof HttpGet) {
			try {
				final HttpGet httpget = (HttpGet) obj;
				httpget.setConfig(requestConfig);
				final UrlEncodedFormEntity uefEntity = setSendParams(param);

				// get请求需要拼接参数
				if (uefEntity != null) {
					requestUrl = requestUrl + "?" + EntityUtils.toString(uefEntity);
				}
				log.info("请求地址:{}",requestUrl);
				final URI uri = new URI(requestUrl);
				httpget.setURI(uri);
				// 请求头信息
				if (headParam != null) {
					final Iterator<Entry<String, Object>> it = headParam.entrySet().iterator();
					while (it.hasNext()) {
						final Entry<String, Object> entry = it.next();
						if (null != entry.getValue()) {
							httpget.addHeader(entry.getKey(), String.valueOf(entry.getValue()));
						}
					}
				}
				final HttpResponse response = httpclient.execute(httpget);
				final HttpEntity entity = response.getEntity();
				final StatusLine statusLine = response.getStatusLine();
				if (uefEntity != null) {
				}

				if (statusLine.getStatusCode() == 200) {
					responseStr = EntityUtils.toString(entity, "utf-8");// 返回请求实体
				} else {
					log.info("httpClient响应状态" + statusLine);
				}
			} catch (final Exception e) {
				e.printStackTrace();
				log.error(e.toString(), e);
				log.error("get请求调用远程方法出现异常：" + requestUrl);
			} finally {
				try {
					httpclient.close();// 关闭httpclient
				} catch (final IOException e) {
					e.printStackTrace();
					log.error(e.toString(), e);
				}
			}
		}

		/**
		 * post请求
		 */
		if (obj instanceof HttpPost) {
			try {
				log.info("请求地址:{}",requestUrl);
				final HttpPost httpPost = new HttpPost();
				httpPost.setConfig(requestConfig);
				final UrlEncodedFormEntity uefEntity = setSendParams(param);

				httpPost.setEntity(uefEntity);
				final URI uri = new URI(requestUrl);
				httpPost.setURI(uri);

				// 请求头信息
				if (headParam != null) {
					final Iterator<Entry<String, Object>> it = headParam.entrySet().iterator();
					while (it.hasNext()) {
						final Entry<String, Object> entry = it.next();
						if (null != entry.getValue()) {
							httpPost.addHeader(entry.getKey(), String.valueOf(entry.getValue()));
						}
					}
				}
				final HttpResponse response = httpclient.execute(httpPost);
				final HttpEntity entity = response.getEntity();
				final StatusLine statusLine = response.getStatusLine();

				if (uefEntity != null) {
					log.info("请求参数:" + EntityUtils.toString(uefEntity));
				}

				if (statusLine.getStatusCode() == 200) {
					responseStr = EntityUtils.toString(entity, "utf-8");// 返回请求实体
				} else {
					log.info("httpClient响应状态" + statusLine);
				}
				log.info("请求返回内容:" + responseStr);

			} catch (final Exception e) {
				e.printStackTrace();
				log.error(e.toString(), e);
				log.error("post请求调用远程方法出现异常：" + requestUrl);
			} finally {
				try {
					httpclient.close();
				} catch (final IOException e) {
					log.error(e.toString(), e);
					e.printStackTrace();
				}
			}

		}
		return responseStr;
	}


	/**
	 * 设置请求参数
	 *
	 * @param map
	 * @return
	 * @throws IOException
	 */
	public static UrlEncodedFormEntity setSendParams(Map<String, Object> map) throws IOException {
		UrlEncodedFormEntity urlentity = null;

		if (null != map) {
			final List<NameValuePair> formparams = new ArrayList<NameValuePair>();
			final Iterator<Entry<String, Object>> it = map.entrySet().iterator();
			while (it.hasNext()) {
				final Entry<String, Object> entry = it.next();
				if (entry.getValue() != null) {
					formparams.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
				}
			}
			urlentity = new UrlEncodedFormEntity(formparams, "UTF-8");
		}
		return urlentity;
	}


}
