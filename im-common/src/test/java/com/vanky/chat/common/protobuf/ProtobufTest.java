package com.vanky.chat.common.protobuf;

import com.google.protobuf.ByteString;
import org.junit.jupiter.api.Test;

/**
 * @author vanky
 * @create 2024/11/2 21:29
 */
public class ProtobufTest {

    @Test
    public void byteStringTest(){
        String str1 = "你好！--- 7892375982";

        ByteString byteString = ByteString.copyFromUtf8(str1);

        byte[] byteArray = byteString.toByteArray();

        ByteString byteString1 = ByteString.copyFrom(byteArray);
        String stringUtf8 = byteString1.toStringUtf8();

        System.out.println(stringUtf8);
    }

}
