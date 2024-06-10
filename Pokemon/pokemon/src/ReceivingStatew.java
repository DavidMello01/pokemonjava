class ReceivingStatew implements State {
    @Override
    public void animate(AnimationPanel panel) {
        // Não há necessidade de animação complexa para receber ataque
        // Podemos apenas adicionar um pequeno "tremor" ou efeito de dano
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 5; i++) {
                        panel.setDefenderX(panel.getDefenderX() + 5);
                        panel.repaint();
                        Thread.sleep(50);
                    }
                    for (int i = 0; i < 5; i++) {
                        panel.setDefenderX(panel.getDefenderX() - 5);
                        panel.repaint();
                        Thread.sleep(50);
                    }
                    panel.setState(new IdleState()); // Volta para o estado ocioso após receber ataque
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
