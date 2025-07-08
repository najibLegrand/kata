// app/layout.tsx
import "./globals.css";
import { Toaster } from 'sonner';

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <html lang="fr">
      <head>
        <title>POC Next.js 15 avec Tailwind CSS</title>
      </head>
      <body>{children} 
        {/* Toaster global (obligatoire pour afficher tout toast déclenché par "sonner") */}
        <Toaster richColors closeButton position="top-right" />
          </body>
    </html>
  );
}
