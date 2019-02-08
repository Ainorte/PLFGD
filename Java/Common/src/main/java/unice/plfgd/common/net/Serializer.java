package unice.plfgd.common.net;

import java.io.*;

public final class Serializer {
    private Serializer(){};

    public static byte[] ObjectToBytes(Exchange object){
        try
        (
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(bos)
        )
        {
            out.writeObject(object);
            out.flush();
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object BytesToObject(byte[] bytes){
        try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes)) {
            try (ObjectInput in = new ObjectInputStream(bis)) {
                return in.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
