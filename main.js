/* ================================================================
   Aurevia Portal | Central JavaScript Logic
================================================================ */

document.addEventListener('DOMContentLoaded', () => {

  /* ── 1. NAVBAR SCROLL EFFECT ── */
  const navbar = document.getElementById('navbar');
  window.addEventListener('scroll', () => {
    if (navbar) navbar.classList.toggle('scrolled', window.scrollY > 50);
  });

  /* ── 2. MOBILE MENU TOGGLE ── */
  const mobChk = document.getElementById('mob-chk');
  document.querySelectorAll('.mobile-nav a').forEach(a => {
    a.addEventListener('click', () => {
      if (mobChk) mobChk.checked = false;
    });
  });

  /* ── 3. DARK MODE TOGGLE ── */
  const themeToggle = document.getElementById('theme-toggle');
  if (themeToggle) {
    const icon = themeToggle.querySelector('i');
    
    // Check saved preference
    if (localStorage.getItem('aurevia-theme') === 'dark') {
      document.documentElement.setAttribute('data-theme', 'dark');
      icon?.classList.replace('fa-moon', 'fa-sun');
    }

    themeToggle.addEventListener('click', () => {
      const isDark = document.documentElement.getAttribute('data-theme') === 'dark';
      if (isDark) {
        document.documentElement.removeAttribute('data-theme');
        localStorage.setItem('aurevia-theme', 'light');
        icon?.classList.replace('fa-sun', 'fa-moon');
      } else {
        document.documentElement.setAttribute('data-theme', 'dark');
        localStorage.setItem('aurevia-theme', 'dark');
        icon?.classList.replace('fa-moon', 'fa-sun');
      }
    });
  }

  /* ── 4. SCROLL FADE-IN ANIMATION ── */
  const fadeTargets = document.querySelectorAll('.fade-in');
  const fadeObserver = new IntersectionObserver((entries) => {
    entries.forEach(entry => {
      if (entry.isIntersecting) {
        entry.target.classList.add('visible');
      }
    });
  }, { threshold: 0.1 });
  fadeTargets.forEach(el => fadeObserver.observe(el));

});