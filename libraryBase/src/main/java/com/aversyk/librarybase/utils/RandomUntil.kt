package com.aversyk.librarybase.utils

import java.util.*

/**
 * 随机数生成
 *
 * @author Averysk
 */
object RandomUntil {

    /**
     * 生成一个0 到 count 之间的随机数
     *
     * @param endNum
     * @return
     */
    fun getNum(endNum: Int): Int {
        if (endNum > 0) {
            val random = Random()
            return random.nextInt(endNum)
        }
        return 0
    }

    /**
     * 生成一个startNum 到 endNum之间的随机数(不包含endNum的随机数)
     *
     * @param startNum
     * @param endNum
     * @return
     */
    fun getNum(startNum: Int, endNum: Int): Int {
        if (endNum > startNum) {
            val random = Random()
            return random.nextInt(endNum - startNum) + startNum
        }
        return 0
    }

    /**
     * 生成随机数字字符串(指定位数)
     *
     * @param size 指定位数
     * @return
     */
    fun getNumAppoint(size: Int): String {
        val buffer = StringBuffer()
        val random = Random()
        for (i in 0 until size) {
            //数字
            buffer.append(random.nextInt(10))
        }
        return buffer.toString()
    }

    /**
     * 生成随机大写字母
     *
     * @return
     */
    val largeLetter: String
        get() {
            val random = Random()
            return (random.nextInt(25) + 'A'.toInt()).toChar().toString()
        }

    /**
     * 生成随机大写字母字符串(指定位数)
     *
     * @param size 指定位数
     * @return
     */
    fun getLargeLetter(size: Int): String {
        val buffer = StringBuffer()
        val random = Random()
        for (i in 0 until size) {
            buffer.append((random.nextInt(25) + 'A'.toInt()).toChar())
        }
        return buffer.toString()
    }

    /**
     * 生成随机小写字母
     *
     * @return
     */
    val smallLetter: String
        get() {
            val random = Random()
            return (random.nextInt(25) + 'a'.toInt()).toChar().toString()
        }

    /**
     * 生成随机小写字母字符串(指定位数)
     *
     * @param size 指定位数
     * @return
     */
    fun getSmallLetter(size: Int): String {
        val buffer = StringBuffer()
        val random = Random()
        for (i in 0 until size) {
            buffer.append((random.nextInt(25) + 'a'.toInt()).toChar())
        }
        return buffer.toString()
    }

    /**
     * 数字与小写字母混编字符串(指定位数)
     *
     * @param size 指定位数
     * @return
     */
    fun getNumSmallLetter(size: Int): String {
        val buffer = StringBuffer()
        val random = Random()
        for (i in 0 until size) {
            if (random.nextInt(2) % 2 == 0) { //字母
                buffer.append((random.nextInt(25) + 'a'.toInt()).toChar())
            } else { //数字
                buffer.append(random.nextInt(10))
            }
        }
        return buffer.toString()
    }

    /**
     * 数字与大写字母混编字符串(指定位数)
     *
     * @param size 指定位数
     * @return
     */
    fun getNumLargeLetter(size: Int): String {
        val buffer = StringBuffer()
        val random = Random()
        for (i in 0 until size) {
            if (random.nextInt(2) % 2 == 0) { //字母
                buffer.append((random.nextInt(25) + 'A'.toInt()).toChar())
            } else { //数字
                buffer.append(random.nextInt(10))
            }
        }
        return buffer.toString()
    }

    /**
     * 数字与大小写字母混编字符串(指定位数)
     *
     * @param size 指定位数
     * @return
     */
    fun getNumLargeSmallLetter(size: Int): String {
        val buffer = StringBuffer()
        val random = Random()
        for (i in 0 until size) {
            if (random.nextInt(2) % 2 == 0) { //字母
                if (random.nextInt(2) % 2 == 0) {
                    buffer.append((random.nextInt(25) + 'A'.toInt()).toChar())
                } else {
                    buffer.append((random.nextInt(25) + 'a'.toInt()).toChar())
                }
            } else { //数字
                buffer.append(random.nextInt(10))
            }
        }
        return buffer.toString()
    }
}