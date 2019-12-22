//
// Created by wisnuprama on 12/22/19.
//

#include <jni.h>
#include <sstream>
#include <random>
#include <string>

const std::string CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

std::string generateUUID(){
    std::string uuid = std::string(36, ' ');
    int rnd = 0;
    int r = 0;

    uuid[8] = '-';
    uuid[13] = '-';
    uuid[18] = '-';
    uuid[23] = '-';

    uuid[14] = '4';

    for(int i=0;i<36;i++){
        if (i != 8 && i != 13 && i != 18 && i != 14 && i != 23) {
            if (rnd <= 0x02) {
                rnd = 0x2000000 + (std::rand() * 0x1000000) | 0;
            }
            rnd >>= 4;
            uuid[i] = CHARS[(i == 19) ? ((rnd & 0xf) & 0x3) | 0x8 : rnd & 0xf];
        }
    }
    return uuid;
}

extern "C" JNIEXPORT jstring JNICALL
Java_id_ac_ui_cs_mobileprogramming_wisnupramadhitya_target_OkrActivity_generateUUID(
        JNIEnv* env,
        jobject /* this */) {
    std::string uuid = generateUUID();
    return env->NewStringUTF(uuid.c_str());
}
