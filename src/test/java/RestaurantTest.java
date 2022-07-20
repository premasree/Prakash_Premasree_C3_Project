import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

class RestaurantTest {
    Restaurant restaurant;

    public RestaurantTest() {
        add_restaurant_menu();
    }
    @Mock
    Restaurant mock_restaurant;

    public void add_restaurant_menu() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        return;

    }
    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){

        mock_restaurant = spy(restaurant);
        when(mock_restaurant.getCurrentTime()).thenReturn(LocalTime.parse("11:00:00"));
        boolean actual = mock_restaurant.isRestaurantOpen();

        assertEquals(true, actual);
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){

        mock_restaurant = spy(restaurant);

        when(mock_restaurant.getCurrentTime()).thenReturn(LocalTime.parse("08:00:00"));
        boolean actual = mock_restaurant.isRestaurantOpen();

        assertEquals(false, actual);

    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    @Test
    public void get_the_price_of_single_item() {
        int expected_price = 119;
        int actual_price = restaurant.getItemPrice("Sweet corn soup");
        assertEquals(expected_price, actual_price);
    }

    @Test
    public void get_the_price_of_single_item_not_present() {
        int expected_price = 0;
        int actual_price = restaurant.getItemPrice("Dummy Dish");
        assertEquals(expected_price, actual_price);
    }

    @Test
    public void get_the_total_price_for_items_selected() {
        int expected_total_price = 119 + 269 ; //price of sweet corn soup and veg lasagne

        List<String> itemList = new ArrayList<>();

        itemList.add("Sweet corn soup");
        itemList.add("Vegetable lasagne");

        int actual_total_price = restaurant.getTotalPriceForItems(itemList);
        assertEquals(expected_total_price, actual_total_price);
    }
}