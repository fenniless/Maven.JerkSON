package io.zipcoder;

import io.zipcoder.utils.Item;
import io.zipcoder.utils.ItemParseException;

import java.util.*;

public class ItemParser {
    int exceptions = 0;
    private Map<String, String> groceryList = new HashMap<>();
    private String[] singleItemArray = new String[11];
    private List<String> oneLineList = new ArrayList<>();

    public List<Item> parseItemList(String valueToParse) {
        List<String> oneLineList = Arrays.asList(valueToParse.split("(##)"));
        List<Item> singleItemList = new ArrayList<>();
        Item newItem = new Item(null, null, null, null);
        for (int i = 0; i < oneLineList.size(); i++) {
            try {
                newItem = parseSingleItem(oneLineList.get(i));
                singleItemList.add(newItem);
            } catch (ItemParseException e) {
                e.printStackTrace();
                exceptions++;
            }
        }
        return singleItemList;
    }

    public Item parseSingleItem(String singleItem) throws ItemParseException {
        try {
            makeSingleItemArray(singleItem);
        } catch (ItemParseException e) {
            throw e;
        }
        String name = getName(singleItem);
        Double price = getPrice(singleItem);
        String type = getType(singleItem);
        String expiration = getExpiration(singleItem);
        Item newItem = new Item(name, price, type, expiration);
        return newItem;
    }

    public void makeSingleItemArray(String singleItem) throws ItemParseException {
        try {
            singleItemArray = singleItem.split("(\\W+)");
        } catch (ArrayIndexOutOfBoundsException a) {
            exceptions++;
            throw new ItemParseException();
        }
        if (singleItemArray.length < 11) {
            exceptions++;
            throw new ItemParseException();
        }
        System.out.println(Arrays.toString(singleItemArray));
    }

    public String getName(String singleItem) {

        return singleItemArray[1].toString().toLowerCase();
    }

    public String getType(String singleItem) {

        return singleItemArray[6].toString().toLowerCase();
    }

    public String getExpiration(String singleItem) {

        return singleItemArray[8].toString() + "/" + singleItemArray[9].toString() + "/" +
                singleItemArray[10].toString();
    }

    public Double getPrice(String singleItem) {
        Double price;
        String priceString = singleItemArray[3].toString() + "." + singleItemArray[4].toString();
        price = Double.valueOf(priceString);
        return price;
    }
}


// cookies
//(\\D+)(\\d)(\\w+)

//
//(\\w+):(\\w+);(\\w+):(\\d\\.\\d{2});(\\w+):(\\w|\\s) (\\d{1,2}\\/\\d{1,2}\\/d{4})(##)