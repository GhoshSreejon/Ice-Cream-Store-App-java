import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test class for IceCreamStore
 * Covers: positive flows, negative flows, edge cases
 */
public class IceCreamStoreTest {
    private IceCreamStore store;

    // Runs before EACH test → ensures clean state
    @BeforeEach
    void setUp() {
        store = new IceCreamStore();
        store.addFlavor("Vanilla", 10);
        store.addFlavor("Chocolate", 5);
    }

    // ✅ Test: add flavors and verify stock
    @Test
    void testAddFlavorAndGetStock() {
        assertEquals(10, store.getStock("Vanilla"));
        assertEquals(5, store.getStock("Chocolate"));
    }

    // ✅ Test: successful order reduces stock
    @Test
    void testOrderFlavorSuccess() {
        store.orderFlavor("Vanilla", 3); // order 3
        assertEquals(7, store.getStock("Vanilla")); // stock = 10 - 3
    }

    // ❌ Test: ordering more than available stock
    @Test
    void testOrderFlavorInsufficientStock() {
        Exception ex = assertThrows(IllegalStateException.class, () -> {
            store.orderFlavor("Chocolate", 10); // only 5 in stock
        });
        assertTrue(ex.getMessage().contains("Not enough stock"));
    }

    // ❌ Test: ordering non-existing flavor
    @Test
    void testOrderInvalidFlavor() {
        assertThrows(IllegalArgumentException.class, () -> {
            store.orderFlavor("Strawberry", 2);
        });
    }

    // ❌ Test: adding a flavor with negative stock
    @Test
    void testNegativeStockAddition() {
        assertThrows(IllegalArgumentException.class, () -> {
            store.addFlavor("Mango", -5);
        });
    }

    // ✅ Test: listing flavors should contain all added ones
    @Test
    void testListFlavors() {
        String flavors = store.listFlavors();
        assertTrue(flavors.contains("Vanilla"));
        assertTrue(flavors.contains("Chocolate"));
    }
}
