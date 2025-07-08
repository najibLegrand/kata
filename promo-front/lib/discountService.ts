import api from "./axios";
import { Cart } from "@/types/Cart";
import { DiscountRequest } from "@/types/DiscountRequest";
import { DiscountResponse } from "@/types/DiscountResponse";

export const fetchCart = async (id: string): Promise<Cart> =>
  (await api.get<Cart>(`/api/v1/discount/cart/${id}`)).data;

export const applyDiscount = async (
  payload: DiscountRequest
): Promise<DiscountResponse> =>
  (await api.post<DiscountResponse>(`/api/v1/discount/apply`, payload)).data;
