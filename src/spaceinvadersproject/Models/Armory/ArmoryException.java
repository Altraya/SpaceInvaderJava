/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvadersproject.Models.Armory;

/**
 * Exception linked with Armory
 * @author Karakayn
 */
public class ArmoryException extends Exception
{   
    @Override
    public String getMessage()
    {
        return "Le test d'ajout de l'arme dans le vaisseau a echoué car l'arme n'est pas liée à l'armurerie";
    }
}

