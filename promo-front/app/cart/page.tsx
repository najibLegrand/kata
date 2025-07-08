"use client";

import { useEffect } from "react";
import { useCartStore } from "@/hooks/useCartStore";
import CartView from "@/components/CartView";
import DiscountInput from "@/components/DiscountInput";
import Toast from "@/components/Toast";

export default function CartPage() {
  const { cart, discount, loading, error, fetchCartById, applyCode, clearError } =
    useCartStore();

  useEffect(() => { fetchCartById("cart123"); }, []);

  return (
    <main className="max-w-3xl mx-auto p-6">
      <h1 className="text-2xl font-bold mb-4">Votre panier</h1>

      {error && <Toast msg={error} onClose={clearError} />}
      {loading && <p className="text-sm text-gray-500">Chargement…</p>}

      {cart && (
        <>
          <CartView cart={cart} discount={discount} />
          <DiscountInput onApply={applyCode} />
        </>
      )}
    </main>
  );
}
