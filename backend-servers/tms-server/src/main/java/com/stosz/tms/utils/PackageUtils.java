package com.stosz.tms.utils;

import com.stosz.plat.utils.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class PackageUtils {

	public static final Logger logger = LoggerFactory.getLogger(PackageUtils.class);

	public static List<Class<? extends Object>> loadClassByFileList(List<String> classNameList, Class<? extends Annotation>... annotations) {
		if (ArrayUtils.isEmpty(classNameList)) {
			return null;
		}
		List<Class<?>> listClass = new ArrayList<Class<?>>();
		int size = classNameList.size();
		for (int i = 0; i < size; i++) {
			String className = classNameList.get(i);
			try {
				Class<?> targetClass = PackageUtils.class.getClassLoader().loadClass(className);
				boolean flag = true;
				if (annotations.length > 0) {
					for (Class<? extends Annotation> annotation : annotations) {
						Annotation target = targetClass.getAnnotation(annotation);
						if (target == null) {
							flag = false;
							continue;
						}
					}
				}
				if (flag) {
					listClass.add(targetClass);
				}
			} catch (ClassNotFoundException e) {
				logger.error(e.getMessage(),e);
			}
		}
		return listClass;
	}

	public static List<String> scanerMessageHandler(String... parentPackage) {
		if (parentPackage.length > 0) {
			StringBuilder builder = new StringBuilder();
			List<String> classNameList = new ArrayList<String>();
			for (int i = 0; i < parentPackage.length; i++) {
				String packageName = parentPackage[i];
				String packageDirName = packageName.replace('.', '/');
				try {
					Enumeration<URL> enumeration = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
					while (enumeration.hasMoreElements()) {
						URL url = enumeration.nextElement();
						String protocol = url.getProtocol();
						if ("file".equals(protocol)) {
							List<String> classList = scanerFileInPackageByFile(packageName, url.getPath(), builder);
							classNameList.addAll(classList);
						} else if ("jar".equals(protocol)) {
							List<String> classList = scannerFileInPackgeByJar(packageName, url, packageDirName);
							classNameList.addAll(classList);
						}
					}
				} catch (IOException e) {
					logger.error(e.getMessage(),e);
				}
			}
			return classNameList;
		}
		return null;
	}

	public static List<String> scannerFileInPackgeByJar(String packageName, URL url, String path) {
		List<String> classNameList = new ArrayList<String>();
		try {
			JarFile jarFile = ((JarURLConnection) url.openConnection()).getJarFile();
			Enumeration<JarEntry> enumeration = jarFile.entries();
			while (enumeration.hasMoreElements()) {
				JarEntry jarEntry = enumeration.nextElement();
				String fileName = jarEntry.getName();
				if (fileName.endsWith(".class") && fileName.startsWith(path)) {
					fileName = fileName.replace("/", ".");
					int lastIndex = fileName.lastIndexOf(".");
					String className = fileName.substring(0, lastIndex);
					classNameList.add(className);
				}
			}
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
		}
		return classNameList;

	}

	public static String getResourcePath(String resourceName) {
		String path = null;
		try {
			Enumeration<URL> enumeration = Thread.currentThread().getContextClassLoader().getResources(resourceName);
			while (enumeration.hasMoreElements()) {
				URL url = enumeration.nextElement();
				if ("file".equalsIgnoreCase(url.getProtocol())) {
					URL resourceUrl = PackageUtils.class.getResource(resourceName);
					if (resourceUrl != null) {
						path = resourceUrl.getPath();
					}
				} else if ("jar".equalsIgnoreCase(url.getProtocol())) {
					logger.info("jar in resource.........");
					JarFile jarFile = ((JarURLConnection) url.openConnection()).getJarFile();
					JarEntry jarEntry = jarFile.getJarEntry(resourceName);
					if (jarEntry != null) {
						path = jarEntry.getName();
					}
				}
			}
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
		}
		return path;
	}

	public static List<String> scanerFileInPackageByFile(String packageName, String packagePath, StringBuilder builder) {
		List<String> list = new ArrayList<String>();
		File file = new File(packagePath);
		if (file.isDirectory()) {
			File fileList[] = file.listFiles();
			for (int i = 0; i < fileList.length; i++) {
				File subFile = fileList[i];
				if (subFile.isDirectory()) {
					String subPackageName = packageName + "." + subFile.getName();
					List<String> classNames = scanerFileInPackageByFile(subPackageName, subFile.getAbsolutePath(), builder);
					list.addAll(classNames);
				} else {
					String fileName = subFile.getName();
					String[] array = fileName.split("[.]");
					if (array.length > 0) {
						String className = array[0];
						builder.delete(0, builder.length());
						builder.append(packageName);
						builder.append(".");
						builder.append(className);
						list.add(builder.toString());
					}
				}
			}
		} else {
			String fileName = file.getName();
			String[] array = fileName.split("[.]");
			if (array.length > 0) {
				String className = array[0];
				builder.delete(0, builder.length());
				builder.append(packageName);
				builder.append(".");
				builder.append(className);
				list.add(builder.toString());
			}
		}
		return list;
	}

	public static List<Method> getMethodByTarget(Class<? extends Object> target, Class<? extends Annotation> annotation) {
		Method[] methodList = target.getDeclaredMethods();
		if (methodList != null && methodList.length > 0) {
			List<Method> methods = new ArrayList<Method>();
			for (int i = 0; i < methodList.length; i++) {
				Method method = methodList[i];
				Annotation targetAnnotation = method.getAnnotation(annotation);
				if (targetAnnotation != null) {
					methods.add(method);
				}
				int len = methodList.length - i - 1;
				if (i >= len) {
					break;
				}
				method = methodList[len];
				targetAnnotation = method.getAnnotation(annotation);
				if (targetAnnotation != null) {
					methods.add(method);
				}
				if (i + 1 == len) {
					break;
				}
			}
			return methods;
		}
		return null;

	}
}
