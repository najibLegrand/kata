import { create } from "zustand";
import { Cart } from "@/types/Cart";
import { DiscountResponse } from "@/types/DiscountResponse";
import { fetchCart, applyDiscount } from "@/lib/discountService";

type State = {
  cart: Cart | null;
  discount: DiscountResponse | null;
  loading: boolean;
  error: string | null;
  fetchCartById: (id: string) => Promise<void>;
  applyCode: (code: string) => Promise<void>;
  clearError: () => void;
};

export const useCartStore = create<State>((set, get) => ({
  cart: null,
  discount: null,
  loading: false,
  error: null,

  fetchCartById: async (id) => {
    set({ loading: true, error: null });
    try {
      const cart = await fetchCart(id);
      set({ cart, loading: false, discount: null });
    } catch (e: any) {
      set({ error: e.message ?? "Erreur chargement panier", loading: false });
    }
  },

  applyCode: async (code) => {
    const { cart } = get();
    if (!cart) return;
    set({ loading: true, error: null });
    try {
      const discount = await applyDiscount({ cart, code });
      set({ discount, loading: false });
    } catch (e: any) {
      set({ error: e.response?.data?.message ?? "Code invalide", loading: false,discount: null });
    }
  },

  clearError: () => set({ error: null }),
}));
