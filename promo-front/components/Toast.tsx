export default function Toast({ msg, onClose }: { msg: string; onClose: () => void }) {
  return (
    <div className="bg-red-100 text-red-800 border border-red-300 rounded p-3 my-2 flex justify-between">
      <span>{msg}</span>
      <button onClick={onClose} className="ml-4">✕</button>
    </div>
  );
}
