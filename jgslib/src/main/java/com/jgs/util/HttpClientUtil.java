package com.jgs.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.jgs.bean.SystemConfig;
import com.jgs.cfg.CFGManager;
import com.jgs.exception.ResponseException;

public class HttpClientUtil {

	private static Logger logger = LogManager.getLogger(HttpClientUtil.class);


	/**
	 * 
	 * Request a get request with data paramter
	 * 
	 * @param url
	 * @param parameters
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String doGet(String url, Map<String, Object> parameters)  {
		// url = URLEncoder.encode(url, "UTF-8");
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse response = null;
			URIBuilder builder = new URIBuilder(url);
			if (parameters != null) {
				Set<String> keys = parameters.keySet();
				for (String key : keys) {
					
					if (parameters.get(key) != null) {
						builder.setParameter(key, parameters.get(key).toString());
					}
				}
			}
			URI uri = builder.build();

			// builder.
			HttpGet httpget = new HttpGet(uri);

			response = httpClient.execute(httpget);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				return EntityUtils.toString(entity);
			}

		} catch (ClientProtocolException e) {
			logger.error("ClientProtocolException when try to get data from ".concat(url), e);
		} catch (IOException e) {
			logger.error("IOException when try to get data from ".concat(url), e);
		} catch (URISyntaxException e) {
			logger.error("URISyntaxException when try to get data from ".concat(url), e);
		}
		return null;
	}

	public static String doPost(String url, Map<String, Object> parameters) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse response = null;
		HttpPost method = new HttpPost(url);

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

		Set<String> keys = parameters.keySet();
		for (String key : keys) {
			nameValuePairs.add(new BasicNameValuePair(key, parameters.get(key).toString()));

		}
		UrlEncodedFormEntity rentity = null;
		try {
			rentity = new UrlEncodedFormEntity(nameValuePairs, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error("UnsupportedEncodingException when try to encode data for ".concat(url), e);
		}
		try {
			method.setEntity(rentity);
			response = httpClient.execute(method);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				return EntityUtils.toString(entity);
			}

		} catch (ClientProtocolException e) {
			logger.error("ClientProtocolException when try to post data to ".concat(url), e);
		} catch (IOException e) {
			logger.error("IOException when try to post data to ".concat(url), e);
		}
		return null;
	}

	
	public static Map<String, Object> getLngAndLat(String address) {
		try {
			return getLngAndLat(address, CFGManager.getProperty(SystemConfig.BAIDU_MAP_KEY));
		} catch (ResponseException e) {

		}

		return null;
	}
	
	public static Map<String, Object> getLngAndLat(String address, String key) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("address", address);
		params.put("output", "json");
		if (key == null) {
			params.put("ak", CFGManager.getProperty(SystemConfig.BAIDU_MAP_KEY));
		} else {
			params.put("ak", key);
		}
		Map<String, Object> location = new HashMap<String, Object>();
		Map<String, Object> result = null;
		try {
			String res = HttpClientUtil.doPost("http://api.map.baidu.com/geocoder/v2/", params);
			result = EcUtil.toMap(res);

			if (result != null && !EcUtil.isEmpty(result.get("result"))) {
				Map<String, Object> locationResult = (Map<String, Object>) result.get("result");
				if (!EcUtil.isEmpty(locationResult.get("location"))) {
					location = (Map<String, Object>) locationResult.get("location");

				}

			}
		} catch (Exception e) {
			logger.error(e);
			throw new ResponseException(e.getMessage());
		}

		if (result != null) {
			Object status = result.get("status");
			if (status == null || !(status.toString().equalsIgnoreCase("0.0") || status.toString().equalsIgnoreCase("0"))) {
				throw new ResponseException("百度地图异常，返回状态码: " + status + " , 请查阅http://developer.baidu.com/map/webservice-geocoding.htm, 8.返回码状态表");
			}
		}
		return location;
	}
	
	
	
	public static Map<String, Object> getAddressByLngAndLat(String lng, String lat, String key) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("location", lng + "," + lat);
		params.put("output", "json");
		params.put("coord_type", "bd09ll");
		if(key == null){
			params.put("ak", CFGManager.getProperty(SystemConfig.BAIDU_MAP_KEY));
		}else{
			params.put("ak", key);
		}
		String res = null;
		try {
			res = HttpClientUtil.doGet("http://api.map.baidu.com/telematics/v3/reverseGeocoding", params);
		} catch (Exception e) {
			logger.error(e);
		}

		Map<String, Object> result = EcUtil.toMap(res);

		return result;
	}
	
	
	public static Map<String, Object> getAddressByLngAndLatV2(String lng, String lat, String key) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("location", lat + "," + lng );
		params.put("output", "json");
		params.put("coord_type", "bd09ll");
		params.put("pois", "0");
		if(key == null){
			params.put("ak", CFGManager.getProperty(SystemConfig.BAIDU_MAP_KEY));
		}else{
			params.put("ak", key);
		}
		String res = null;
		try {
			res = HttpClientUtil.doGet("http://api.map.baidu.com/geocoder/v2/", params);
		} catch (Exception e) {
			logger.error(e);
		}

		Map<String, Object> result = EcUtil.toMap(res);
		System.out.println(result.get("result"));

		return result;
	}
	
	
	

}
