class Time {

    private int hours;
    private int minutes;
    private int seconds;

    public Time(int hours, int minutes, int seconds) {
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    @Override
    public String toString() {
        String hours;
        String minutes;
        String seconds;
        if ((this.hours + "").toString().length() == 1) {
            hours = "0" + this.hours;
        } else {
            hours = this.hours + "";
        }
        if ((this.minutes + "").toString().length() == 1) {
            minutes = "0" + this.minutes;
        } else {
            minutes = this.minutes + "";
        }
        if ((this.seconds + "").toString().length() == 1) {
            seconds = "0" + this.seconds;
        } else {
            seconds = this.seconds + "";
        }
        return hours + ":" + minutes + ":" + seconds;
    }
}