package com.thuggeelya.rent;

import com.thuggeelya.rent.dto.ItemDTO;
import com.thuggeelya.rent.model.Item;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MapperTest {

    @Test
    public void mapWithNulls() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setSkipNullEnabled(true);

        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setName("new");
        itemDTO.setCost(100L);

        Item item = new Item();
        item.setId(1L);
        item.setName("old");
        item.setCost(150L);
        item.setSerialNumber("10101010");
        item.setDescription("cool");

        mapper.map(itemDTO, item);

        Item expected = new Item();
        expected.setId(1L);
        expected.setName("new");
        expected.setCost(100L);
        expected.setDescription("cool");
        expected.setSerialNumber("10101010");

        assertEquals(expected, item);
    }
}
