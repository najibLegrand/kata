"use client";
import { useState } from "react";

export default function DiscountInput({ onApply }: { onApply: (code: string) => void }) {
  const [code, setCode] = useState("");
  return (
    <div className="flex gap-2 mt-4">
      <input
        value={code}
        onChange={(e) => setCode(e.target.value)}
        placeholder="Code promo"
        className="flex-1 border rounded px-3 py-2"
      />
      <button
        onClick={() => code && onApply(code)}
        className="bg-blue-600 hover:bg-blue-700 text-white rounded px-4"
      >
        Appliquer
      </button>
    </div>
  );
}
