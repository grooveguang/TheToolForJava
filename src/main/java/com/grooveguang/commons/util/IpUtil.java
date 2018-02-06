package com.grooveguang.commons.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

/**
 * IP获取工具类
 * 
 * @author groovyguang
 *
 */
public abstract class IpUtil {
	
	/** 日志 */
	private static Logger log = Logger.getLogger(IpUtil.class);
	
	private static InetAddress localHost;
	
	static{
		try {
			localHost = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			log.error(e);
		}
	}
	
	/**
     * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址,
     *
     * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？
     * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。
     *
     * 如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130,
     * 192.168.1.100
     *
     * 用户真实IP为： 192.168.1.110
	 * 
	 * @param request
	 * @return IP
	 */
	public static String getIpAddr(HttpServletRequest request) {
		if (request == null) {
			return "unknown";
		}
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Forwarded-For");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Real-IP");
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	/**
	 * get host ip address for system like with windows or linux
	 * 
	 * @return hostIpAddress
	 */
	public static String getHostIp() {
		String ipAddress = "";
		if(isWindowsOS()) {
			if(localHost!=null){
				ipAddress = localHost.getHostAddress();
			}else {
				return "UnknownHostAddr";
			}
		}else {
			ipAddress = getLinuxLocalIp();
		}
		return ipAddress;
	}
	
	/**
	 * @return 主机名称
	 */
	public static final String getHostName() {
		if(localHost!=null){
			return localHost.getHostName();
		}
		return "UnknownHostName";
	}
	
	/**
	 * @return 主机地址
	 */
	public static final String getHostAddress(){
		if(localHost!=null){
			return localHost.getHostAddress();
		}
		return "UnknownHostAddr";
	}
	
    /**
     * 判断操作系统是否是Windows
     *
     * @return
     */
    public static boolean isWindowsOS() {
        boolean isWindowsOS = false;
        String osName = System.getProperty("os.name");
        if (osName.toLowerCase().indexOf("windows") > -1) {
            isWindowsOS = true;
        }
        return isWindowsOS;
    }
    
    /**
     * 获取Linux下的IP地址
     *
     * @return IP地址
     * @throws SocketException
     */
    private static  String getLinuxLocalIp(){
        String ip = "";
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                String name = intf.getName();
                if (!name.contains("docker") && !name.contains("lo")) {
                    for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                        InetAddress inetAddress = enumIpAddr.nextElement();
                        if (!inetAddress.isLoopbackAddress()) {
                            String ipaddress = inetAddress.getHostAddress().toString();
                            if (!ipaddress.contains("::") && !ipaddress.contains("0:0:") && !ipaddress.contains("fe80")) {
                                ip = ipaddress;
                            }
                        }
                    }
                }
            }
        } catch (SocketException ex) {
//            System.out.println("获取ip地址异常");
//            ip = "127.0.0.1";
            ex.printStackTrace();
        }
        return ip;
    }
	
    
    public static void main(String[] args) {
		String hostIp = getHostIp();
		System.out.println("IP地址是:"+hostIp+"是否是Windows系统:"+isWindowsOS());
	}
}
