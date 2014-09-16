package com.sromku.bugsnag.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.apache.commons.codec.binary.Base64;

public class SerializationUtils {

	public static String serialize(Serializable object) {
		String encoded = null;
		try {
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
			objectOutputStream.writeObject(object);
			objectOutputStream.close();
			encoded = Base64.encodeBase64String(byteArrayOutputStream.toByteArray());
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return encoded;
	}

	@SuppressWarnings("unchecked")
	public static <T extends Serializable> T deserialize(String string, Class<T> clazz) {
		byte[] bytes = Base64.decodeBase64(string.getBytes());
		T object = null;
		try {
			ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(bytes));
			object = (T) objectInputStream.readObject();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}
}
