local lock_key = KEYS[1]
local mutex_id = ARGV[1]
local lock_id = redis.call('HGET', lock_key, "ID");
if (lock_id) then
    # 锁被占用,判断是不是自己占用
    if (lock_id == mutex_id) then
        # 自己占用, -1
        redis.call("HINCRBY", lock_key, "lock_count", "-1");
        local lock_count = redis.call("HGET", lock_key, "lock_count");
        if (lock_count == "0") then
            redis.call("DEL", lock_key);
        end
        return lock_count;
    else
        # 不是自己占用
        return 0
    end
else
    # 锁没有被占用, 返回 0
    return 0
end