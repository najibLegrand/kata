import { Product } from "./Product";
export interface Cart { cartId: string; products: Product[]; total: number; }