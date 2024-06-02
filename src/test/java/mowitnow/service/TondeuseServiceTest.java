package mowitnow.service;

import mowitnow.dto.Pelouse;
import mowitnow.dto.Tondeuse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TondeuseServiceTest {

    @Test
    void testPositionnerTondeuse(){
        // Arrange
        TondeuseService tondeuseService = new TondeuseService();

        // Act
        var reponse = tondeuseService.positionnerTondeuse();

        // Assert
        assertTrue(reponse);
    }

    @Test
    void testChangerOrientationCasD(){
        // Arrange
        var tondeuseService = new TondeuseService();
        var tondeuse = new Tondeuse();
        tondeuse.setOrientation('N');

        // Act
        tondeuseService.changerOrientation(tondeuse, 'D');

        // Assert
        assertEquals('E', tondeuse.getOrientation());
    }

    @Test
    void testChangerOrientationCasG(){
        // Arrange
        var tondeuseService = new TondeuseService();
        var tondeuse = new Tondeuse();
        tondeuse.setOrientation('N');

        // Act
        tondeuseService.changerOrientation(tondeuse, 'G');

        // Assert
        assertEquals('W', tondeuse.getOrientation());
    }

    @Test
    void testChangerOrientationCasA(){
        // Arrange
        var tondeuseService = new TondeuseService();
        var pelouse = new Pelouse();
        pelouse.setCoinSuperieurDroitX(5);
        pelouse.setCoinSuperieurDroitY(5);
        var tondeuse = new Tondeuse();
        tondeuse.setPelouse(pelouse);
        tondeuse.setPositionX(1);
        tondeuse.setPositionY(2);
        tondeuse.setOrientation('E');

        // Act
        tondeuseService.changerOrientation(tondeuse, 'A');

        // Assert
        assertEquals('E', tondeuse.getOrientation());
        assertEquals(2, tondeuse.getPositionX());
    }
}
