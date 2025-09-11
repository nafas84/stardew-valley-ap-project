package io.Ap.StardewValley.Gson;



import io.Ap.StardewValley.Common.Model.Shop.BlackSmith.BlackSmith;
import io.Ap.StardewValley.Common.Model.Shop.CarpentersShop.CarpentersShop;
import io.Ap.StardewValley.Common.Model.Shop.FishShop.FishShop;
import io.Ap.StardewValley.Common.Model.Shop.JojaMart.JojaMart;
import io.Ap.StardewValley.Common.Model.Shop.MarniesRanch.MarniesRanch;
import io.Ap.StardewValley.Common.Model.Shop.PierresGeneralStore.PierresStore;
import io.Ap.StardewValley.Common.Model.Shop.Shop;
import io.Ap.StardewValley.Common.Model.Shop.TheStardropSaloon.TheStardropSaloon;
import com.google.gson.*;

import java.lang.reflect.Type;

public class ShopAdapter implements JsonSerializer<Shop>, JsonDeserializer<Shop> {

    @Override
    public JsonElement serialize(Shop src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("typeGson", src.getClass().getSimpleName());
        jsonObject.add("data", context.serialize(src));
        return jsonObject;
    }

    @Override
    public Shop deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String type = jsonObject.get("typeGson").getAsString();

        switch (type) {
            case "BlackSmith":
                return context.deserialize(jsonObject.get("data"), BlackSmith.class);
            case "JojaMart":
                return context.deserialize(jsonObject.get("data"), JojaMart.class);
            case "PierresStore":
                return context.deserialize(jsonObject.get("data"), PierresStore.class);
            case "CarpentersShop":
                return context.deserialize(jsonObject.get("data"), CarpentersShop.class);
            case "FishShop":
                return context.deserialize(jsonObject.get("data"), FishShop.class);
            case "MarniesRanch":
                return context.deserialize(jsonObject.get("data"), MarniesRanch.class);
            case "TheStardropSaloon":
                return context.deserialize(jsonObject.get("data"), TheStardropSaloon.class);

            default:
                throw new JsonParseException("Unknown shop type: " + type);
        }
    }
}
