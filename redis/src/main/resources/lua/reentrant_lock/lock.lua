local lock_key = KEYS[1]
local mutex_id = ARGV[1]
local expire_seconds = ARGV[2]
local lock_id = redis.call('HGET', lock_key, "ID");
if (lock_id) then
    # 锁被占用,判断是不是自己占用
    if (lock_id == mutex_id) then
        # 自己占用, + 1, 并更新过期时间
        redis.call("HINCRBY", lock_key, "lock_count", "1");
        redis.call("EXPIRE", lock_key, expire_seconds);
        local lock_count = redis.call("HGET", lock_key, "lock_count");
        return lock_count;
    else
        # 不是自己占用
        return 0
    end
else
    # 锁没有被占用,占用锁并返回 1 ,表示成功获取锁
    redis.call("HMSET", lock_key, "ID", mutex_id, "lock_count", "1");
    redis.call("EXPIRE", lock_key, expire_seconds);
    return 1
end