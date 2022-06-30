package LessonThreeMediumBattleship;

class WrongLocationException extends Exception {
        public WrongLocationException(){}

        public WrongLocationException(String message){
            super(message);

    }
}