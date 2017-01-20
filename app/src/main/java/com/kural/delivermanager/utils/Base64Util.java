package com.kural.delivermanager.utils;

/**
 * base64通用操作封装<BR>
 * [功能详细描述]
 *
 */
public abstract class Base64Util
{



    /**
     * 将byte[]进行base64编码
     *
     * @param input 要编码的byte[]
     * @return 编码后字符串
     */
    public static String encode(byte[] input)
    {

        byte[] ret = Base64.encode(input);
        return new String(ret);
    }

    /**
     * 将stringacod进行base64解码
     *
     * @param stringacod 要解码的字符串
     * @return 解码后byte[]
     */
    public static byte[] decode(String stringacod)
    {
        byte[] ret = Base64.decode(stringacod);
        if (ret == null)
        {
            return null;
        }
        else
        {
            return ret;
        }
    }

}
