#include <jni.h>
#include "org_siegan_dojo_thing_Snake.h"
#include <iostream>
#include <termios.h>
#include <unistd.h>

using namespace std;

void resetRawMode(const termios &orig_termios) {
    tcsetattr(STDIN_FILENO, TCSAFLUSH, &orig_termios);
}

JNIEXPORT void JNICALL Java_org_siegan_dojo_thing_Snake_terminalRawToNormal
  (JNIEnv *, jclass){

        termios orig_termios;

        // mendapatkan ang akan diisi dengan atribut terminal saat ini.
        // Struktur termios digunakan untuk menyimpan berbagai pengaturan dan mode operasi terminal,
        // seperti mode input, mode output, kontrol sinyal, dan lain-lain.
        tcgetattr(STDIN_FILENO, &orig_termios);

        resetRawMode(orig_termios);

  }