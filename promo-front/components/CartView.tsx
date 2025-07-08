import { Cart } from "@/types/Cart";
import { DiscountResponse } from "@/types/DiscountResponse";

const format = (n: number) =>
  new Intl.NumberFormat("fr-FR", { style: "currency", currency: "EUR" }).format(n);

export default function CartView({ cart, discount }: { cart: Cart; discount: DiscountResponse | null }) {
  const totalAfter = discount
    ? cart.total - discount.discountAmount
    : cart.total;

  return (
    <div className="border rounded-xl p-4 shadow">
      <ul className="divide-y">
        {cart.products.map((p) => (
          <li key={p.id} className="py-2 flex justify-between">
            <span>{p.name}</span>
            <span>{format(p.price)}</span>
          </li>
        ))}
      </ul>
      <hr className="my-4" />
      {discount && (
        <div className="flex justify-between text-green-600">
          <span>Réduction :</span>
          <span>-{format(discount.discountAmount)}</span>
        </div>
      )}
      <div className="flex justify-between font-bold text-lg mt-1">
        <span>Total :</span>
        <span>{format(totalAfter)}</span>
      </div>
    </div>
  );
}
