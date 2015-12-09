
package javaapplication7;

import java.util.Random;
import java.util.Scanner;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.RaspiPin;
/**
 *
 * @author Admin
 */
public class JavaApplication7 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
// system objects
                final GpioController gpio = GpioFactory.getInstance();
                final GpioPinDigitalInput Boton1 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_02, PinPullResistance.PULL_DOWN);
                final GpioPinDigitalInput Boton2 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_03, PinPullResistance.PULL_DOWN);
                final GpioPinDigitalInput Boton3 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_04, PinPullResistance.PULL_DOWN);

		Scanner in = new Scanner(System.in);
		Random rand =new Random();
                boolean accion=false; 
                ascii impresion= new ascii();
		// Game variables
		String[] enemies = { "OSCAR", "ABRAHAM", "GERMAN", "MAGADAN", "EL PROFE", "GIGAPROFE"};
		int maxEnemyHealth = 75;
		int enemyAttackDamage = 15;
                int enemy = 0;
	
		// Player variables
		int health = 100;
		int attackDamage = 25;
		int numHealthPots = 3;
		int healthPotionHealAmount = 30;
		int healthPotionDropChance = 50; //percentage that you'll get a new potion when you kill a mob
		
		boolean running = true;

                System.out.println(impresion.Inicio); //
                while(!Boton1.isHigh()&&!Boton2.isHigh()&&!Boton3.isHigh()){
                //do nothing while it cycles
                }
		try{Thread.sleep(500);}catch(InterruptedException ex){};
		GAME: // Label so that we can loop back when if we want to continue the game
		while(health>1 && enemy<6){			
			int enemyHealth = rand.nextInt(maxEnemyHealth);
			
                       
			System.out.println("\t# " + enemies[enemy] + " aparecio! #\n");
			
			while(enemyHealth > 0) {										// Give you options to fight, drink a health pot or run when the enemy HP > 0
				accion=false;
                                String monstAscii=impresion.retString(enemy);
                                System.out.println(monstAscii);
                                System.out.println("\tThe quedan:" + health+ "puntos de vida.");						
				System.out.println("\t" + enemies[enemy] + "tiene " + enemyHealth + "puntos de vida restantes.");
				System.out.println("\n\t QUE QUIERES HACER?");
				System.out.println("\t1. Atacar");
				System.out.println("\t2. Beber pocion recuperadora");
				System.out.println("\t3. Rendirte");
				
				String input ="df";
                               				/* The damage done is random the player damage goes from 0 - 25
				 * 25 damage is a critical hit
				 * 0 damage is either a miss or a dodge
				 */
                                
                                while( accion==false){
				if(Boton1.isHigh()) {									
					int damageDealt = rand.nextInt(attackDamage);
					int damageTaken = rand.nextInt(enemyAttackDamage);
					
					enemyHealth -= damageDealt;
					health -= damageTaken;
                                        
	
					System.out.println("\t> Le ocasionaste a " + enemy + " un daño de " + damageDealt + " puntos de vida");
					System.out.println("\t> Recibiste " + damageTaken + " puntos de dano");
                                        accion=true;
                                   
					try{
                                          Thread.sleep(1000);
                                        }catch(InterruptedException ex){

                                        }
					if(health < 1) {
						System.out.println("\t> Has sido vencido.");
						break;
					}
				}
				/* 
				 * 
				 */
				else if(Boton2.isHigh()) {
					if(numHealthPots > 0) {
						health += healthPotionHealAmount;
						numHealthPots--;
						System.out.println("\t> Tomas una pocion que te cura " + healthPotionHealAmount
								+ "\n\t> Ahora tienes" + health + " puntos de vida"
								+ "\n\t> Te quedan " + numHealthPots + " pociones. \n");
						
					}
					else {
						System.out.println("\t> No tienes ya pociones, vence enemigos para obtener mas");
					}
				}
				else if(Boton3.isHigh()) {
					System.out.println("\t The rendiste cobardemente ante " + enemies[enemy] + "!");
					health=-1;
                                        break;
				}
				
                                }
			}
			
			if(health < 1) {
				System.out.println("Has muerto");
				break;
			}
			
			System.out.println("----------------------------");
			System.out.println(" # Venciste a  " + enemy + " # \n");
                        enemy++;
                        if(enemy<6){
			System.out.println(" # Te quedan " + health + "puntos de vida.");
			if(rand.nextInt(100) > healthPotionDropChance) {
				numHealthPots++;
				System.out.println(  enemy + "solto una pocion");
				System.out.println(" # You now have " + numHealthPots + "health pot(s)");
			}
                        accion=false;
			System.out.println("----------------------------");
			System.out.println("Que procede?");
			System.out.println("1. Siguiente oponente");
			System.out.println("2. Escapar del laberinto");
			

			
			while(accion==false){
                            
                            if(Boton1.isHigh()) {
				System.out.println("You continiue on your adventure!");
                                accion=true;
                                try{Thread.sleep(500);}catch(InterruptedException ex){};
                            }
                            else if(Boton2.isHigh()) {
				System.out.println("Te has rendido cobardemente");
                                try{Thread.sleep(500);}catch(InterruptedException ex){};
				break;
                            }
                        }
                        }
                }
		if(enemy<6)
		System.out.println(impresion.Ganaste); 
                else
                    System.out.println(impresion.perdiste);
    }
    
}
