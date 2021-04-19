local lock_key = KEYS[1]
local mutex_id = ARGV[1]
local lock_id = redis.call('HGET', lock_key, "ID");
if (lock_id) then
    if (lock_id == mutex_id) then
        redis.call("HINCRBY", lock_key, "lock_count", "-1");
        local lock_count = redis.call("HGET", lock_key, "lock_count");
        if (lock_count == "0") then
            redis.call("DEL", lock_key);
        end
        return lock_count;
    else
        return "0"
    end
else
    return "0"
end