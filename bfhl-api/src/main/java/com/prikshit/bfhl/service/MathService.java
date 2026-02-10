package com.prikshit.bfhl.service;

import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class MathService {

    public List<Integer> fibonacci(int n) {
        if (n < 0 || n > 1000)
            throw new IllegalArgumentException("Invalid fibonacci input");

        List<Integer> result = new ArrayList<>();
        int a = 0, b = 1;

        for (int i = 0; i < n; i++) {
            result.add(a);
            int temp = a + b;
            a = b;
            b = temp;
        }
        return result;
    }

    public List<Integer> primes(List<Integer> nums) {
        List<Integer> res = new ArrayList<>();
        for (int n : nums) {
            if (isPrime(n)) res.add(n);
        }
        return res;
    }

    private boolean isPrime(int n) {
        if (n < 2) return false;
        for (int i = 2; i * i <= n; i++)
            if (n % i == 0) return false;
        return true;
    }

    public int hcf(List<Integer> nums) {
        int res = nums.get(0);
        for (int n : nums)
            res = gcd(res, n);
        return res;
    }

    public int lcm(List<Integer> nums) {
        int res = nums.get(0);
        for (int n : nums)
            res = (res * n) / gcd(res, n);
        return res;
    }

    private int gcd(int a, int b) {
        return b == 0 ? Math.abs(a) : gcd(b, a % b);
    }
}
