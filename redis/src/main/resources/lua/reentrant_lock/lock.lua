local lock_key = KEYS[1]
local mutex_id = ARGV[1]
local expire_seconds = ARGV[2]
local lock_id = redis.call('HGET', lock_key, "ID");
if (lock_id) then
    if (lock_id == mutex_id) then
        redis.call("HINCRBY", lock_key, "lock_count", "1");
        redis.call("EXPIRE", lock_key, expire_seconds);
        local lock_count = redis.call("HGET", lock_key, "lock_count");
        return lock_count;
    else
        return "0"
    end
else
    redis.call("HMSET", lock_key, "ID", mutex_id, "lock_count", "1");
    redis.call("EXPIRE", lock_key, expire_seconds);
    return "1"
end