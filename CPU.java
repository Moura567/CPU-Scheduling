/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package os.ass22;

/**
 *
 * @author Lenovo
 */
public class CPU {
    CPU() {
        this.CurrentProcess = new Process();
    }

    public Process CurrentProcess;
    public int Timer;

    public void CurrentP(Process currentP) {
        CurrentProcess = currentP;
    }

}