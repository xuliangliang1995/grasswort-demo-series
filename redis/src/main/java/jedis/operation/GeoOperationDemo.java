package jedis.operation;

import jedis.JedisSentinelPoolFactory;
import redis.clients.jedis.*;
import redis.clients.jedis.commands.ProtocolCommand;
import redis.clients.jedis.util.SafeEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author 🌺xuliangliang🌺
 * @Description
 * @Date 2021/4/18
 */
public class GeoOperationDemo {

    public static void main(String[] args) {
        JedisSentinelPool sentinelPool = JedisSentinelPoolFactory.sentinelPool();
        String positionKey = "positions";
        try (Jedis jedis = sentinelPool.getResource()) {
            jedis.del(positionKey);
            jedis.geoadd(positionKey, 116.301582, 39.992439, "海淀公园");
            jedis.geoadd(positionKey, 116.406516, 39.912414, "天安门");
            jedis.geoadd(positionKey, 116.326891, 39.900649, "北京西站");
            jedis.geoadd(positionKey, 116.387395, 39.872554, "北京南站");

            Double distance1 = jedis.geodist(positionKey, "天安门", "海淀公园");
            System.out.println("天安门 >>>> 海淀公园 " + distance1 + "M");

            Double distance2 = jedis.geodist(positionKey, "天安门", "北京西站");
            System.out.println("天安门 >>>> 北京西站 " + distance2 + "M");

            Double distance3 = jedis.geodist(positionKey, "天安门", "北京南站");
            System.out.println("天安门 >>>> 北京南站 " + distance3 + "M");

            List<GeoRadiusResponse> addresses = jedis.georadiusByMember(positionKey, "天安门", 11, GeoUnit.KM);
            System.out.println("天安门 11 km 内的地点有:" + addresses.stream().map(GeoRadiusResponse::getMemberByString)
                    .reduce("", (a, b) -> a + " " + b));

            List<GeoCoordinate> coordinates = jedis.geopos(positionKey, "天安门");
            coordinates.forEach(coordinate -> System.out.println("天安门 " + coordinate));

            /// geosearch 命令没从 jedis api 中找到, 通过 command 实现一下
            // GEOSEARCH positions FROMLONLAT 116.403894 39.914534 BYBOX 10 10 km
            Object res = jedis.sendCommand(GeoSearchCommand.command, positionKey, "FROMLONLAT",
                    "116.403894", "39.914534", "BYBOX", "10", "10", "km");
            if (res instanceof List) {
                ((List<?>) res).forEach(bytes -> System.out.println(new String((byte[])bytes)));
            }
        }
        sentinelPool.destroy();
    }

    /**
     * GEOSEARCH
     */
    static class GeoSearchCommand implements ProtocolCommand {

        private GeoSearchCommand() {
        }

        public static final GeoSearchCommand command = new GeoSearchCommand();

        @Override
        public byte[] getRaw() {
            return SafeEncoder.encode("GEOSEARCH".toLowerCase(Locale.ENGLISH));
        }
    }
}
